package edu.ifsp.seunutri.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ifsp.seunutri.modelo.Ficha;
import edu.ifsp.seunutri.modelo.Usuario;
import edu.ifsp.seunutri.modelo.Prato;
import edu.ifsp.seunutri.repository.FichaRepository;
import edu.ifsp.seunutri.repository.PratoRepository;
import edu.ifsp.seunutri.repository.UsuarioRepository;

@Service
public class FichaService {

	private final FichaRepository fichaRepository;
	private final PratoRepository pratoRepository;
	private final UsuarioRepository usuarioRepository;

	public FichaService(FichaRepository fichaRepository, PratoRepository pratoRepository, UsuarioRepository usuarioRepository) {
		this.fichaRepository = fichaRepository;
		this.pratoRepository = pratoRepository;
		this.usuarioRepository = usuarioRepository;
	}

	public List<Ficha> listarTodos() {
		return fichaRepository.findAll();
	}

	public Ficha buscarPorId(Long id) {
		return fichaRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Ficha não encontrada!"));
	}

	@Transactional
	public Ficha gerarFicha(Usuario usuario) {
		int idade = usuario.getIdade();
		if (idade < 19) {
			throw new IllegalArgumentException("Idade fora da faixa mínima de 19 anos: " + idade);
		}

		String sexo = usuario.getSexo();
		String nivel = usuario.getNivelAtividade();

		double min;
		double max;

		if (idade >= 61) {
			if ("F".equalsIgnoreCase(sexo)) {
				min = 1500;
				max = 1700;
			} else {
				min = 2400;
				max = 2600;
			}
		} else if ("F".equalsIgnoreCase(sexo)) {
			if ("sedentaria".equalsIgnoreCase(nivel) || "sedentário".equalsIgnoreCase(nivel)) {
				min = 1600;
				max = 2000;
			} else {
				min = 2200;
				max = 2400;
			}
		} else {
			if ("sedentario".equalsIgnoreCase(nivel) || "sedentário".equalsIgnoreCase(nivel)) {
				min = 2200;
				max = 2600;
			} else {
				min = 2600;
				max = 3000;
			}
		}

		List<Prato> pratosDisponiveis = pratoRepository.findAll();
		pratosDisponiveis.sort((p1, p2) -> p1.getId().compareTo(p2.getId()));

		List<Prato> pratosSelecionados = new ArrayList<>();
		double soma = 0;

		for (Prato prato : pratosDisponiveis) {
			if (soma >= min && soma <= max) {
				break;
			}
			double novoSoma = soma + prato.getValorNutricional();
			if (novoSoma <= max) {
				pratosSelecionados.add(prato);
				soma = novoSoma;
			}
		}

		if (soma < min || soma > max) {
			throw new IllegalStateException(
				"Não foi possível montar ficha dentro da faixa calórica [" + min + ", " + max + "]. Soma obtida: " + soma);
		}

		Ficha ficha = new Ficha();
		ficha.setUsuarioCPF(usuario.getCpf());
		ficha.setImc(0);
		ficha.setPeso(usuario.getPeso());
		ficha.setPratos(pratosSelecionados);

		return fichaRepository.save(ficha);
	}

	public double calcularTotalCalorias(Ficha ficha) {
		double total = 0;
		for (Prato prato : ficha.getPratos()) {
			total += prato.getValorNutricional();
		}
		return total;
	}

	@Transactional
	public Ficha gerarFichaPorUsuarioId(Long id) {
		Usuario usuario = usuarioRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
		return gerarFicha(usuario);
	}

	public String calcularFaixaCalorica(Usuario usuario) {
		int idade = usuario.getIdade();
		String sexo = usuario.getSexo();
		String nivel = usuario.getNivelAtividade();

		double min;
		double max;

		if (idade >= 61) {
			if ("F".equalsIgnoreCase(sexo)) {
				min = 1500;
				max = 1700;
			} else {
				min = 2400;
				max = 2600;
			}
		} else if ("F".equalsIgnoreCase(sexo)) {
			if ("sedentaria".equalsIgnoreCase(nivel) || "sedentário".equalsIgnoreCase(nivel)) {
				min = 1600;
				max = 2000;
			} else {
				min = 2200;
				max = 2400;
			}
		} else {
			if ("sedentario".equalsIgnoreCase(nivel) || "sedentário".equalsIgnoreCase(nivel)) {
				min = 2200;
				max = 2600;
			} else {
				min = 2600;
				max = 3000;
			}
		}

		return (int) min + " a " + (int) max + " kcal";
	}

}
