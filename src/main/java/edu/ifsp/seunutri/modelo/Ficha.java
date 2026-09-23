package edu.ifsp.seunutri.modelo;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

public class Ficha {
	private int imc;
	private double peso;
	
	@Id
	@ManyToAny
	@JoinColumn(name = "Usuario_cpf")
	private int usuarioCPF;
	private int idPrato;
	private int idRanque;
}
