package edu.ifsp.seunutri.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.ifsp.seunutri.modelo.Ficha;
import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final FichaService fichaService;

	public UsuarioService(UsuarioRepository usuarioRepository, FichaService fichaService) {
		this.usuarioRepository = usuarioRepository;
		this.fichaService = fichaService;
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
		Usuario salvo = usuarioRepository.save(usuario);
		try {
			fichaService.gerarFicha(salvo);
		} catch (Exception e) {
			System.out.println("Não foi possível gerar ficha automática para o usuário " + salvo.getCpf() + ": " + e.getMessage());
		}
		return salvo;
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
		existente.setNivelAtividade(usuario.getNivelAtividade());
		Usuario salvo = usuarioRepository.save(existente);
		try {
			fichaService.gerarFicha(salvo);
		} catch (Exception e) {
			System.out.println("Não foi possível regenerar ficha automática para o usuário " + salvo.getCpf() + ": " + e.getMessage());
		}
		return salvo;
	}

	public List<Usuario> listarUsuariosSemFicha() {
		List<Usuario> todos = usuarioRepository.findAll();
		List<Ficha> fichas = fichaService.listarTodos();
		Set<Long> cpfsComFicha = new HashSet<>();
		for (Ficha f : fichas) {
			cpfsComFicha.add(f.getUsuarioCPF());
		}
		List<Usuario> semFicha = new ArrayList<>();
		for (Usuario u : todos) {
			if (!cpfsComFicha.contains(u.getCpf())) {
				semFicha.add(u);
			}
		}
		return semFicha;
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
