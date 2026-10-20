package edu.ifsp.seunutri.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ifsp.seunutri.modelo.Ranque;
import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.repository.RanqueRepository;
import edu.ifsp.seunutri.service.UsuarioService;

@Service
public class RanqueService {

	private final RanqueRepository ranqueRepository;
	private final UsuarioService usuarioService;

	public RanqueService(RanqueRepository ranqueRepository, UsuarioService usuarioService) {
		this.ranqueRepository = ranqueRepository;
		this.usuarioService = usuarioService;
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

	// Grupo calculado na hora a partir do IMC do ranque, sem coluna nova.
	// imc é int: <=18 Grupo 1, 19-24 Grupo 2, 25-29 Grupo 3, >=30 Grupo 4.
	public String grupoDoImc(int imc) {
		if (imc <= 18) {
			return "Grupo 1";
		}
		if (imc <= 24) {
			return "Grupo 2";
		}
		if (imc <= 29) {
			return "Grupo 3";
		}
		return "Grupo 4";
	}

	// Usuários do mesmo grupo do usuarioLogado, sem ADMIN, ordenados por pontuação desc.
	public List<Usuario> listarUsuariosDoGrupo(Usuario usuarioLogado) {
		if (usuarioLogado == null) {
			return List.of();
		}
		Optional<Ranque> ranqueUsuario = ranqueRepository.findById(usuarioLogado.getIdRanque());
		if (ranqueUsuario.isEmpty()) {
			return List.of();
		}
		String grupo = grupoDoImc(ranqueUsuario.get().getImc());

		Map<Integer, Ranque> ranquesPorId = new HashMap<>();
		for (Ranque r : ranqueRepository.findAll()) {
			ranquesPorId.put(r.getId(), r);
		}

		List<Usuario> usuariosDoGrupo = new ArrayList<>();
		for (Usuario u : usuarioService.listarTodos()) {
			if ("ADMIN".equals(u.getPapel())) {
				continue; // ADMIN (sys) nunca aparece para o usuário comum
			}
			Ranque r = ranquesPorId.get(u.getIdRanque());
			if (r != null && grupo.equals(grupoDoImc(r.getImc()))) {
				usuariosDoGrupo.add(u);
			}
		}

		usuariosDoGrupo.sort((u1, u2) -> {
			double p1 = ranquesPorId.get(u1.getIdRanque()).getPontuacao();
			double p2 = ranquesPorId.get(u2.getIdRanque()).getPontuacao();
			return Double.compare(p2, p1); // maior pontuação primeiro
		});

		return usuariosDoGrupo;
	}
}
