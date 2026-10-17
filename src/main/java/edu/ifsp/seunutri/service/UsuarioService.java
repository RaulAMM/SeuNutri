package edu.ifsp.seunutri.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public List<Usuario> listarTodos() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> buscarPorId(Long id) {
		return usuarioRepository.findById(id);
	}

	public Usuario salvarUsuario(Usuario usuario) {
		if (usuario != null && usuarioRepository.existsById(usuario.getCpf())) {
			throw new RuntimeException("Este usuario ja existe");
		}
		return usuarioRepository.save(usuario);
	}

	public Usuario atualizarUsuario(Long id, Usuario usuario) {
		Usuario existente = usuarioRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		existente.setNome(usuario.getNome());
		existente.setLocalizacao(usuario.getLocalizacao());
		existente.setIdade(usuario.getIdade());
		existente.setPeso(usuario.getPeso());
		existente.setEmail(usuario.getEmail());
		existente.setSexo(usuario.getSexo());
		existente.setIdRanque(usuario.getIdRanque());
		return usuarioRepository.save(existente);
	}

	public void excluirUsuario(Long id) {
		try {
			usuarioRepository.deleteById(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new IllegalStateException("Não é possível excluir este usuário porque ele está vinculado a uma ficha.");
		}
	}

	public void acharUsuario(Usuario usuario) {
		Usuario usuarioExistente = usuarioRepository.findById(usuario.getCpf())
			.orElseThrow(() -> new RuntimeException("ID não encontrado!"));
		System.out.println("Usuario encontrado: " + usuarioExistente.getNome());
	}
}
