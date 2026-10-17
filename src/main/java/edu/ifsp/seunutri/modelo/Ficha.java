package edu.ifsp.seunutri.modelo;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "ficha")
public class Ficha {

	private int imc;
	private double peso;

	@Id
	@Column(name = "usuario_cpf")
	private long usuarioCPF;

	@ManyToMany
	@JoinTable(
		name = "ficha_prato",
		joinColumns = @JoinColumn(name = "ficha_usuario_cpf"),
		inverseJoinColumns = @JoinColumn(name = "prato_id")
	)
	private List<Prato> pratos;

	public int getImc() {
		return imc;
	}

	public void setImc(int imc) {
		this.imc = imc;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public long getUsuarioCPF() {
		return usuarioCPF;
	}

	public void setUsuarioCPF(long usuarioCPF) {
		this.usuarioCPF = usuarioCPF;
	}

	public List<Prato> getPratos() {
		return pratos;
	}

	public void setPratos(List<Prato> pratos) {
		this.pratos = pratos;
	}

}
