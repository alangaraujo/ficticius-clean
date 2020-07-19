package com.alanaraujo.ficticiusclean.api.dto;

import javax.validation.constraints.NotNull;

public class RankingConsumo {

	@NotNull
	public double valorCombustivel;
	
	@NotNull
	public int kmTotalCidade;
	
	@NotNull
	public int kmTotalRodovia;

	public double getValorCombustivel() {
		return valorCombustivel;
	}

	public void setValorCombustivel(double valorCombustivel) {
		this.valorCombustivel = valorCombustivel;
	}

	public int getKmTotalCidade() {
		return kmTotalCidade;
	}

	public void setKmTotalCidade(int kmTotalCidade) {
		this.kmTotalCidade = kmTotalCidade;
	}

	public int getKmTotalRodovia() {
		return kmTotalRodovia;
	}

	public void setKmTotalRodovia(int kmTotalRodovia) {
		this.kmTotalRodovia = kmTotalRodovia;
	}

}
