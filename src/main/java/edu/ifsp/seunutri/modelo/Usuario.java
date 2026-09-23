package edu.ifsp.seunutri.modelo;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

public class Usuario {
	@Id
	private int cpf;
	private String nome;
	private String localizacao;
	private int idade;
	private double peso;
	private String email;
	private String sexo;

	@ManyToAny
	@JoinColumn(name = "Id_ranque")
	private int idRanque;

	public int getCpf() {
		return cpf;
	}

	public void setCpf(int cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public int getIdRanque() {
		return idRanque;
	}

	public void setIdRanque(int idRanque) {
		this.idRanque = idRanque;
	}

}
