package com.alanaraujo.ficticiusclean.api.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class SalvarVeiculoInput {
    
	@NotNull
    public String nome;
    
	@NotNull
    public Date dataFabricacao;
    
	@NotNull
	public int consumoCidadeKmL;
    
	@NotNull
	public int consumoRodoviaKmL;
    
	public ReferencedId<Long> modelo;
    
    public SalvarVeiculoInput() {
    }

    public SalvarVeiculoInput(String nome, ReferencedId<Long> modelo, Date dataFabricacao, int consumoCidadeKmL, int consumoRodoviaKmL) {
        this.nome = nome;
        this.modelo = modelo;
        this.dataFabricacao = dataFabricacao;
        this.consumoCidadeKmL = consumoCidadeKmL;
        this.consumoRodoviaKmL = consumoRodoviaKmL;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ReferencedId<Long> getModelo() {
        return modelo;
    }

    public void setModelo(ReferencedId<Long> modelo) {
        this.modelo = modelo;
    }

    public Date getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(Date dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public int getConsumoCidadeKmL() {
        return consumoCidadeKmL;
    }

    public void setConsumoCidadeKmL(int consumoCidadeKmL) {
        this.consumoCidadeKmL = consumoCidadeKmL;
    }

    public int getConsumoRodoviaKmL() {
        return consumoRodoviaKmL;
    }

    public void setConsumoRodoviaKmL(int consumoRodoviaKmL) {
        this.consumoRodoviaKmL = consumoRodoviaKmL;
    }
    
}
