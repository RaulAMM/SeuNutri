package edu.ifsp.seunutri.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prato")
public class Prato {
	@Id
	private int id;
	private String ingredientes;
	private float valorNutricional;
	private String receita;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}

	public float getValorNutricional() {
		return valorNutricional;
	}

	public void setValorNutricional(float valorNutricional) {
		this.valorNutricional = valorNutricional;
	}

	public String getReceita() {
		return receita;
	}

	public void setReceita(String receita) {
		this.receita = receita;
	}

}
