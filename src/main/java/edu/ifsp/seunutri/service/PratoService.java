package edu.ifsp.seunutri.service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ifsp.seunutri.modelo.Prato;
import edu.ifsp.seunutri.repository.PratoRepository;

@Service
public class PratoService {

	private final PratoRepository pratoRepository;

	public PratoService(PratoRepository pratoRepository) {
		this.pratoRepository = pratoRepository;
	}

	public List<Prato> listarTodos() {
		return pratoRepository.findAll();
	}

	public List<Prato> listarFiltrados(String nome, String ingredientesEvitar) {
		List<Prato> todos = pratoRepository.findAll();
		String nomeNormalizado = normalizar(nome);
		List<String> evitar = separarIngredientes(ingredientesEvitar);

		return todos.stream()
			.filter(prato -> nomeNormalizado.isEmpty()
				|| normalizar(prato.getNome()).contains(nomeNormalizado))
			.filter(prato -> evitar.isEmpty()
				|| evitar.stream().noneMatch(ev
					-> normalizar(prato.getIngredientes()).contains(ev)))
			.toList();
	}

	private String normalizar(String texto) {
		if (texto == null) {
			return "";
		}
		return Normalizer.normalize(texto.toLowerCase().trim(), Normalizer.Form.NFD)
			.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	private List<String> separarIngredientes(String texto) {
		if (texto == null || texto.isBlank()) {
			return List.of();
		}
		return Arrays.stream(texto.split(","))
			.map(String::trim)
			.filter(s -> !s.isEmpty())
			.map(this::normalizar)
			.toList();
	}

	public Optional<Prato> buscarPorId(Long id) {
		return pratoRepository.findById(id);
	}

	public Prato salvarPrato(Prato prato) {
		if (prato != null && prato.getId() != null && pratoRepository.existsById(prato.getId())) {
			throw new RuntimeException("Este prato ja existe");
		}
		return pratoRepository.save(prato);
	}

	public Prato atualizarPrato(Long id, Prato prato) {
		Prato existente = pratoRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		existente.setNome(prato.getNome());
		existente.setIngredientes(prato.getIngredientes());
		existente.setValorNutricional(prato.getValorNutricional());
		existente.setReceita(prato.getReceita());
		return pratoRepository.save(existente);
	}

	public void excluirPrato(Long id) {
		try {
			pratoRepository.deleteById(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new IllegalStateException("Não é possível excluir este prato porque ele está vinculado a uma ficha.");
		}
	}

	public void acharPrato(Prato prato) {
	    Prato pratoExistente = pratoRepository.findById(prato.getId())
	        .orElseThrow(() -> new RuntimeException("ID não encontrado!"));

	    System.out.println("Prato encontrado: " + pratoExistente.getNome());
	}
}
