package com.alanaraujo.ficticiusclean.api.dto;

import javax.validation.constraints.NotNull;

public class SalvarMarcaInput {
    
	@NotNull
    public String nome;

    public SalvarMarcaInput() {
    }

    public SalvarMarcaInput(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}

