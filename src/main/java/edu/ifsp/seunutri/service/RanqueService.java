package edu.ifsp.seunutri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ifsp.seunutri.modelo.Ranque;
import edu.ifsp.seunutri.repository.RanqueRepository;

@Service
public class RanqueService {

	private final RanqueRepository ranqueRepository;

	public RanqueService(RanqueRepository ranqueRepository) {
		this.ranqueRepository = ranqueRepository;
	}

	public List<Ranque> listarTodos() {
		return ranqueRepository.findAll();
	}

	public Optional<Ranque> buscarPorId(Integer id) {
		return ranqueRepository.findById(id);
	}

	public Ranque salvarRanque(Ranque ranque) {
		if (ranque != null && ranqueRepository.existsById(ranque.getId())) {
			throw new RuntimeException("Este ranque ja existe");
		}
		return ranqueRepository.save(ranque);
	}

	public Ranque atualizarRanque(Integer id, Ranque ranque) {
		Ranque existente = ranqueRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		existente.setImc(ranque.getImc());
		existente.setPontuacao(ranque.getPontuacao());
		existente.setRanquecol(ranque.getRanquecol());
		return ranqueRepository.save(existente);
	}

	public void excluirRanque(Integer id) {
		try {
			ranqueRepository.deleteById(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new IllegalStateException("Não é possível excluir este ranque porque ele está vinculado a um usuário ou a uma ficha.");
		}
	}

	public void acharRanque(Ranque ranque) {
		Ranque ranqueExistente = ranqueRepository.findById(ranque.getId())
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		System.out.println("Ranque encontrado: " + ranqueExistente.getRanquecol());
	}
}
