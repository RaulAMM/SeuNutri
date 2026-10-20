package edu.ifsp.seunutri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.repository.UsuarioRepository;
import edu.ifsp.seunutri.security.SenhaService;

@Controller
public class LoginController {

	private final UsuarioRepository usuarioRepository;
	private final SenhaService senhaService;

	public LoginController(UsuarioRepository usuarioRepository, SenhaService senhaService) {
		this.usuarioRepository = usuarioRepository;
		this.senhaService = senhaService;
	}

	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		if (usuarioLogado != null) {
			return "redirect:/";
		}
		model.addAttribute("erro", null);
		return "login";
	}

	@PostMapping("/login")
	public String autenticar(@RequestParam("email") String email,
			@RequestParam("senha") String senha, HttpSession session, Model model) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		if (usuario == null) {
			model.addAttribute("erro", "E-mail ou senha invalidos.");
			return "login";
		}
		if (!senhaService.verificar(senha, usuario.getSenha())) {
			model.addAttribute("erro", "E-mail ou senha invalidos.");
			return "login";
		}
		session.setAttribute("usuarioLogado", usuario);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
