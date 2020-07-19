package com.alanaraujo.ficticiusclean.api.dto;

import javax.validation.constraints.NotNull;

public class SalvarModeloInput {
	
	@NotNull
    public String nome;
	
    public ReferencedId<Long> marca;

    public SalvarModeloInput() {
    }

    public SalvarModeloInput(String nome, ReferencedId<Long> marca) {
        this.nome = nome;
        this.marca = marca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ReferencedId<Long> getMarca() {
        return marca;
    }

    public void setMarca(ReferencedId<Long> marca) {
        this.marca = marca;
    }
    
}
