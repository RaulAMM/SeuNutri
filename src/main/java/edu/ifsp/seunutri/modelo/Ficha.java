package edu.ifsp.seunutri.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "ficha")
public class Ficha {
	
	private int imc;
	private double peso;
	
	@Id
	@Column(name = "usuario_cpf")
	private long usuarioCPF;

	@Column(name = "id_prato")
	private long idPrato;

	@Column(name = "id_ranque")
	private int idRanque;

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

	public long getIdPrato() {
		return idPrato;
	}

	public void setIdPrato(long idPrato) {
		this.idPrato = idPrato;
	}

	public int getIdRanque() {
		return idRanque;
	}

	public void setIdRanque(int idRanque) {
		this.idRanque = idRanque;
	}
	
	
}
