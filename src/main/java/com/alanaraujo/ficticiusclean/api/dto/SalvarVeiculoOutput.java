package com.alanaraujo.ficticiusclean.api.dto;

import java.util.Date;

public class SalvarVeiculoOutput {
    
	public Long id;
    public String nome;
    public Date dataFabricacao;
    public int consumoCidadeKmL;
    public int consumoRodoviaKmL;
    public SalvarModeloOutput modelo;

    public SalvarVeiculoOutput() {
    }

    public SalvarVeiculoOutput(Long id, String nome, Date dataFabricacao, int consumoCidadeKmL, int consumoRodoviaKmL) {
        this.id = id;
    	this.nome = nome;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

	public SalvarModeloOutput getModelo() {
		return modelo;
	}

	public void setModelo(SalvarModeloOutput modelo) {
		this.modelo = modelo;
	}
    
}
