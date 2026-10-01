package edu.ifsp.seunutri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ifsp.seunutri.modelo.Prato;
import edu.ifsp.seunutri.service.PratoService;

@Controller
@RequestMapping("/pratos")
public class PratoController {

	private final PratoService pratoService;

	public PratoController(PratoService pratoService) {
		this.pratoService = pratoService;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("pratos", pratoService.listarTodos());
		return "pratos/listar";
	}

	@GetMapping("/novo")
	public String formularioNovo(Model model) {
		model.addAttribute("prato", new Prato());
		return "pratos/formulario";
	}

	@PostMapping
	public String salvar(@ModelAttribute Prato prato) {
		pratoService.salvarPrato(prato);
		return "redirect:/pratos";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable Long id, Model model) {
		Prato prato = pratoService.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		model.addAttribute("prato", prato);
		return "pratos/formulario";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable Long id, @ModelAttribute Prato prato) {
		pratoService.atualizarPrato(id, prato);
		return "redirect:/pratos";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable Long id, org.springframework.ui.Model model) {
		try {
			pratoService.excluirPrato(id);
		} catch (IllegalStateException e) {
			model.addAttribute("erro", e.getMessage());
			model.addAttribute("pratos", pratoService.listarTodos());
			return "pratos/listar";
		}
		return "redirect:/pratos";
	}
}
