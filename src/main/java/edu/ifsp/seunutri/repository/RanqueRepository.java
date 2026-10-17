package edu.ifsp.seunutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifsp.seunutri.modelo.Ranque;

@Repository
public interface RanqueRepository extends JpaRepository<Ranque, Integer> {
}
