package edu.ifsp.seunutri.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ranque {
	@Id
	private int id;
	private int imc;
	private double pontuacao;
	private String ranquecol;

	public int getImc() {
		return imc;
	}

	public void setImc(int imc) {
		this.imc = imc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public String getRanquecol() {
		return ranquecol;
	}

	public void setRanquecol(String ranquecol) {
		this.ranquecol = ranquecol;
	}

}
