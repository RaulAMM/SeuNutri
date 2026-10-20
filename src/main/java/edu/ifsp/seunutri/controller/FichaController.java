package edu.ifsp.seunutri.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

import edu.ifsp.seunutri.modelo.Ficha;
import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.service.FichaService;
import edu.ifsp.seunutri.service.UsuarioService;

@Controller
@RequestMapping("/fichas")
public class FichaController {

	private final FichaService fichaService;
	private final UsuarioService usuarioService;

	public FichaController(FichaService fichaService, UsuarioService usuarioService) {
		this.fichaService = fichaService;
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public String listar(Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		boolean admin = usuarioLogado != null && "ADMIN".equals(usuarioLogado.getPapel());

		List<Ficha> fichas = fichaService.listarTodos();
		List<Usuario> usuarios = usuarioService.listarTodos();

		if (!admin && usuarioLogado != null) {
			fichas = fichas.stream()
				.filter(f -> f.getUsuarioCPF() == usuarioLogado.getCpf())
				.toList();
			usuarios = usuarios.stream()
				.filter(u -> u.getCpf() == usuarioLogado.getCpf())
				.toList();
		}

		Map<Long, String> faixas = new HashMap<>();
		Map<Long, Double> totais = new HashMap<>();

		for (Ficha ficha : fichas) {
			for (Usuario usuario : usuarios) {
				if (usuario.getCpf() == ficha.getUsuarioCPF()) {
					faixas.put(ficha.getUsuarioCPF(), fichaService.calcularFaixaCalorica(usuario));
					totais.put(ficha.getUsuarioCPF(), fichaService.calcularTotalCalorias(ficha));
					break;
				}
			}
		}

		model.addAttribute("fichas", fichas);
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("faixas", faixas);
		model.addAttribute("totais", totais);
		model.addAttribute("usuariosSemFicha", usuarioService.listarUsuariosSemFicha());

		return "fichas/listar";
	}

	@GetMapping("/visualizar/{id}")
	public String visualizar(@PathVariable("id") Long id, Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		boolean admin = usuarioLogado != null && "ADMIN".equals(usuarioLogado.getPapel());

		Ficha ficha = fichaService.buscarPorId(id);
		if (!admin && usuarioLogado != null && ficha.getUsuarioCPF() != usuarioLogado.getCpf()) {
			return "redirect:/fichas";
		}

		Usuario usuario = null;
		for (Usuario u : usuarioService.listarTodos()) {
			if (u.getCpf() == ficha.getUsuarioCPF()) {
				usuario = u;
				break;
			}
		}
		model.addAttribute("ficha", ficha);
		model.addAttribute("usuario", usuario);
		model.addAttribute("faixa", fichaService.calcularFaixaCalorica(usuario));
		model.addAttribute("totalCalorias", fichaService.calcularTotalCalorias(ficha));
		return "fichas/visualizar";
	}

	@PostMapping("/gerar/{id}")
	public String gerar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null) {
			return "redirect:/login";
		}
		boolean admin = "ADMIN".equals(usuarioLogado.getPapel());
		Long cpfParaGerar;
		if (admin) {
			// ADMIN pode gerar para qualquer CPF informado na URL.
			cpfParaGerar = id;
		} else {
			// USUARIO: ignora qualquer CPF do formulário/URL e usa sempre o da sessão.
			cpfParaGerar = usuarioLogado.getCpf();
		}
		// Se o usuário já tem ficha, não gerar outra.
		boolean jaTemFicha = fichaService.listarTodos().stream()
			.anyMatch(f -> f.getUsuarioCPF() == cpfParaGerar);
		if (jaTemFicha) {
			redirectAttributes.addFlashAttribute("erro", "Este usuário já possui ficha.");
			return "redirect:/fichas";
		}
		try {
			fichaService.gerarFichaPorUsuarioId(cpfParaGerar);
			redirectAttributes.addFlashAttribute("mensagem", "Ficha gerada automaticamente.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("erro", "Não foi possível gerar ficha: " + e.getMessage());
		}
		return "redirect:/fichas";
	}
}
