package edu.ifsp.seunutri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("usuarios", usuarioService.listarTodos());
		return "usuarios/listar";
	}

	@GetMapping("/novo")
	public String formularioNovo(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuarios/formulario";
	}

	@PostMapping
	public String salvar(@ModelAttribute Usuario usuario) {
		usuarioService.salvarUsuario(usuario);
		return "redirect:/usuarios";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable("id") Long id, Model model) {
		Usuario usuario = usuarioService.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		model.addAttribute("usuario", usuario);
		return "usuarios/formulario";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable("id") Long id, @ModelAttribute Usuario usuario) {
		usuarioService.atualizarUsuario(id, usuario);
		return "redirect:/usuarios";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable("id") Long id, Model model) {
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
