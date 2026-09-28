package services;

import java.util.Objects;


import org.springframework.stereotype.Service;

import edu.ifsp.seunutri.modelo.Prato;
import repository.PratoRepository;

@Service
public class PratoService {

	private final PratoRepository pratoRepository;
	
	public PratoService(PratoRepository pratoRepository) { 
		this.pratoRepository = pratoRepository; 
	}

	public void acharPrato(Prato prato) {
	    Prato pratoExistente = pratoRepository.findById(prato.getId())
	        .orElseThrow(() -> new RuntimeException("ID não encontrado!"));
	        
	    System.out.println("Prato encontrado: " + pratoExistente.getNome());
	}
	
	public Prato salvarPrato(Prato prato) {
		if (Objects.nonNull(prato)) {
			throw new RuntimeException("Este prato ja existe");
		} else {
			return pratoRepository.save(prato);
		}
	}
}
