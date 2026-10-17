package edu.ifsp.seunutri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ifsp.seunutri.modelo.Ficha;
import edu.ifsp.seunutri.repository.FichaRepository;

@Service
public class FichaService {

	private final FichaRepository fichaRepository;

	public FichaService(FichaRepository fichaRepository) {
		this.fichaRepository = fichaRepository;
	}

	public List<Ficha> listarTodos() {
		return fichaRepository.findAll();
	}

	public Optional<Ficha> buscarPorId(Long id) {
		return fichaRepository.findById(id);
	}

	public Ficha salvarFicha(Ficha ficha) {
		if (ficha != null && fichaRepository.existsById(ficha.getUsuarioCPF())) {
			throw new RuntimeException("Esta ficha ja existe");
		}
		return fichaRepository.save(ficha);
	}

	public Ficha atualizarFicha(Long id, Ficha ficha) {
		Ficha existente = fichaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		existente.setImc(ficha.getImc());
		existente.setPeso(ficha.getPeso());
		existente.setIdPrato(ficha.getIdPrato());
		existente.setIdRanque(ficha.getIdRanque());
		return fichaRepository.save(existente);
	}

	public void excluirFicha(Long id) {
		try {
			fichaRepository.deleteById(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new IllegalStateException("Não é possível excluir esta ficha.");
		}
	}

	public void acharFicha(Ficha ficha) {
		Ficha fichaExistente = fichaRepository.findById(ficha.getUsuarioCPF())
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		System.out.println("Ficha encontrada: " + fichaExistente.getUsuarioCPF());
	}
}
