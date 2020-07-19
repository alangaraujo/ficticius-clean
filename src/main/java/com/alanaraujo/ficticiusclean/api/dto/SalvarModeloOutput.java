package com.alanaraujo.ficticiusclean.api.dto;

public class SalvarModeloOutput {
    
    public Long id;
	public String nome;
	public SalvarMarcaOutput marca;
    
    public SalvarModeloOutput() {
    }

	public SalvarModeloOutput(Long id, String nome) {
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

	public SalvarMarcaOutput getMarca() {
		return marca;
	}

	public void setMarca(SalvarMarcaOutput marca) {
		this.marca = marca;
	}
    
}
