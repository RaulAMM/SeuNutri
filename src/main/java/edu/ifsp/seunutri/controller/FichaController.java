package edu.ifsp.seunutri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ifsp.seunutri.modelo.Ficha;
import edu.ifsp.seunutri.service.FichaService;

@Controller
@RequestMapping("/fichas")
public class FichaController {

	private final FichaService fichaService;

	public FichaController(FichaService fichaService) {
		this.fichaService = fichaService;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("fichas", fichaService.listarTodos());
		return "fichas/listar";
	}

	@GetMapping("/novo")
	public String formularioNovo(Model model) {
		model.addAttribute("ficha", new Ficha());
		return "fichas/formulario";
	}

	@PostMapping
	public String salvar(@ModelAttribute Ficha ficha) {
		fichaService.salvarFicha(ficha);
		return "redirect:/fichas";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable("id") Long id, Model model) {
		Ficha ficha = fichaService.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		model.addAttribute("ficha", ficha);
		return "fichas/formulario";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable("id") Long id, @ModelAttribute Ficha ficha) {
		fichaService.atualizarFicha(id, ficha);
		return "redirect:/fichas";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable("id") Long id, Model model) {
		try {
			fichaService.excluirFicha(id);
		} catch (IllegalStateException e) {
			model.addAttribute("erro", e.getMessage());
			model.addAttribute("fichas", fichaService.listarTodos());
			return "fichas/listar";
		}
		return "redirect:/fichas";
	}
}
