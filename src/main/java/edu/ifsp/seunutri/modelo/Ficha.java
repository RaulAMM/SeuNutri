package edu.ifsp.seunutri.modelo;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "ficha")
public class Ficha {
	
	private int imc;
	private double peso;
	
	@Id
	@ManyToAny
	@JoinColumn(name = "Usuario_cpf")
	private int usuarioCPF;
	
	@ManyToAny
	@JoinColumn(name = "Id_prato")
	private int idPrato;
	
	@ManyToAny
	@JoinColumn(name = "Id_ranque")
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

	public int getUsuarioCPF() {
		return usuarioCPF;
	}

	public void setUsuarioCPF(int usuarioCPF) {
		this.usuarioCPF = usuarioCPF;
	}

	public int getIdPrato() {
		return idPrato;
	}

	public void setIdPrato(int idPrato) {
		this.idPrato = idPrato;
	}

	public int getIdRanque() {
		return idRanque;
	}

	public void setIdRanque(int idRanque) {
		this.idRanque = idRanque;
	}
	
	
}
