package com.alanaraujo.ficticiusclean.api.dto;

public class SalvarMarcaOutput {

	public Long id;
	public String nome;

	public SalvarMarcaOutput() {
	}

	public SalvarMarcaOutput(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
