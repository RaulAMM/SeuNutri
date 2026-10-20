package edu.ifsp.seunutri.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

import edu.ifsp.seunutri.modelo.Ranque;
import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.service.RanqueService;
import edu.ifsp.seunutri.service.UsuarioService;

@Controller
@RequestMapping("/ranques")
public class RanqueController {

	private final RanqueService ranqueService;
	private final UsuarioService usuarioService;

	public RanqueController(RanqueService ranqueService, UsuarioService usuarioService) {
		this.ranqueService = ranqueService;
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public String listar(Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		boolean admin = usuarioLogado != null && "ADMIN".equals(usuarioLogado.getPapel());

		if (admin) {
			List<Ranque> ranques = ranqueService.listarTodos();
			model.addAttribute("ranques", ranques);
		} else if (usuarioLogado != null) {
			List<Usuario> usuariosDoGrupo = ranqueService.listarUsuariosDoGrupo(usuarioLogado);
			Optional<Ranque> ranqueUsuario = ranqueService.buscarPorId(usuarioLogado.getIdRanque());
			String grupo = ranqueUsuario
				.map(r -> ranqueService.grupoDoImc(r.getImc()))
				.orElse("");

			Map<Integer, Double> pontuacaoPorRanque = new HashMap<>();
			for (Ranque r : ranqueService.listarTodos()) {
				pontuacaoPorRanque.put(r.getId(), r.getPontuacao());
			}
			Map<Long, Double> pontuacoes = new HashMap<>();
			for (Usuario u : usuariosDoGrupo) {
				pontuacoes.put(u.getCpf(), pontuacaoPorRanque.getOrDefault(u.getIdRanque(), 0.0));
			}

			model.addAttribute("usuariosDoGrupo", usuariosDoGrupo);
			model.addAttribute("grupo", grupo);
			model.addAttribute("pontuacoes", pontuacoes);
		}

		return "ranques/listar";
	}

	@GetMapping("/novo")
	public String formularioNovo(Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/ranques";
		}
		model.addAttribute("ranque", new Ranque());
		return "ranques/formulario";
	}

	@PostMapping
	public String salvar(@ModelAttribute Ranque ranque, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/ranques";
		}
		ranqueService.salvarRanque(ranque);
		return "redirect:/ranques";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable("id") Integer id, Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/ranques";
		}
		Ranque ranque = ranqueService.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		model.addAttribute("ranque", ranque);
		return "ranques/formulario";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable("id") Integer id, @ModelAttribute Ranque ranque, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/ranques";
		}
		ranqueService.atualizarRanque(id, ranque);
		return "redirect:/ranques";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable("id") Integer id, Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/ranques";
		}
		try {
			ranqueService.excluirRanque(id);
		} catch (IllegalStateException e) {
			model.addAttribute("erro", e.getMessage());
			model.addAttribute("ranques", ranqueService.listarTodos());
			return "ranques/listar";
		}
		return "redirect:/ranques";
	}
}
