package edu.ifsp.seunutri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifsp.seunutri.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByCpfAndNome(long cpf, String nome);

	Usuario findByEmail(String email);

}
