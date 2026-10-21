package edu.ifsp.seunutri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.List;

import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.repository.UsuarioRepository;
import edu.ifsp.seunutri.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;
	private final UsuarioRepository usuarioRepository;

	public UsuarioController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
		this.usuarioService = usuarioService;
		this.usuarioRepository = usuarioRepository;
	}

	@GetMapping
	public String listar(Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		List<Usuario> todos = usuarioService.listarTodos();
		if (usuarioLogado != null && !"ADMIN".equals(usuarioLogado.getPapel())) {
			todos = todos.stream()
				.filter(u -> u.getCpf() == usuarioLogado.getCpf())
				.toList();
		}
		model.addAttribute("usuarios", todos);
		return "usuarios/listar";
	}

	@GetMapping("/novo")
	public String formularioNovo(Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado != null && !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/";
		}
		if (!model.containsAttribute("usuario")) {
			model.addAttribute("usuario", new Usuario());
		}
		return "usuarios/formulario";
	}

	@PostMapping
	public String salvar(@ModelAttribute("usuario") Usuario usuario, HttpSession session, RedirectAttributes redirectAttributes) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado != null && !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/";
		}
		boolean admin = usuarioLogado != null && "ADMIN".equals(usuarioLogado.getPapel());

		if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
			redirectAttributes.addFlashAttribute("erro", "Este e-mail já está cadastrado.");
			usuario.setSenha(null);
			redirectAttributes.addFlashAttribute("usuario", usuario);
			return "redirect:/usuarios/novo";
		}

		usuario.setIdRanque(2); // ranque padrão fixo para novo usuário (id 2 = "Peso normal" em data.sql)

		usuarioService.salvarUsuario(usuario);

		if (admin) {
			return "redirect:/usuarios";
		}
		redirectAttributes.addFlashAttribute("sucesso", "Conta criada com sucesso. Faça login para entrar.");
		return "redirect:/login";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable("id") Long id, Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || (!"ADMIN".equals(usuarioLogado.getPapel()) && usuarioLogado.getCpf() != id)) {
			return "redirect:/usuarios";
		}
		Usuario usuario = usuarioService.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		model.addAttribute("usuario", usuario);
		return "usuarios/formulario";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable("id") Long id, @ModelAttribute("usuario") Usuario usuario, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || (!"ADMIN".equals(usuarioLogado.getPapel()) && usuarioLogado.getCpf() != id)) {
			return "redirect:/usuarios";
		}
		Usuario existente = usuarioService.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		usuario.setIdRanque(existente.getIdRanque()); // preserva o ranque atual na edição (campo removido do formulário)
		usuarioService.atualizarUsuario(id, usuario);
		return "redirect:/usuarios";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable("id") Long id, Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado == null || !"ADMIN".equals(usuarioLogado.getPapel())) {
			return "redirect:/usuarios";
		}
		try {
			usuarioService.excluirUsuario(id);
		} catch (IllegalStateException e) {
			model.addAttribute("erro", e.getMessage());
			model.addAttribute("usuarios", usuarioService.listarTodos());
			return "usuarios/listar";
		}
		return "redirect:/usuarios";
	}
}
