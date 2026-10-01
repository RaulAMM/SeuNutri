package edu.ifsp.seunutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifsp.seunutri.modelo.Prato;

@Repository
public interface PratoRepository extends JpaRepository<Prato, Long>{
	Prato findByIdAndNome(Long id, String nome);
	
}