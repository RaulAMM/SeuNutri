package edu.ifsp.seunutri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.ifsp.seunutri.modelo.Ranque;
import edu.ifsp.seunutri.service.RanqueService;

@Controller
@RequestMapping("/ranques")
public class RanqueController {

	private final RanqueService ranqueService;

	public RanqueController(RanqueService ranqueService) {
		this.ranqueService = ranqueService;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("ranques", ranqueService.listarTodos());
		return "ranques/listar";
	}

	@GetMapping("/novo")
	public String formularioNovo(Model model) {
		model.addAttribute("ranque", new Ranque());
		return "ranques/formulario";
	}

	@PostMapping
	public String salvar(@ModelAttribute Ranque ranque) {
		ranqueService.salvarRanque(ranque);
		return "redirect:/ranques";
	}

	@GetMapping("/{id}/editar")
	public String formularioEditar(@PathVariable("id") Integer id, Model model) {
		Ranque ranque = ranqueService.buscarPorId(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		model.addAttribute("ranque", ranque);
		return "ranques/formulario";
	}

	@PostMapping("/{id}")
	public String atualizar(@PathVariable("id") Integer id, @ModelAttribute Ranque ranque) {
		ranqueService.atualizarRanque(id, ranque);
		return "redirect:/ranques";
	}

	@PostMapping("/{id}/excluir")
	public String excluir(@PathVariable("id") Integer id, Model model) {
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
