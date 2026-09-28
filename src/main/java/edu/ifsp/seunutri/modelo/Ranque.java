package edu.ifsp.seunutri.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ranque")
public class Ranque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
