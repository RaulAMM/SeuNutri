package edu.ifsp.seunutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifsp.seunutri.modelo.Ficha;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Long> {
}
