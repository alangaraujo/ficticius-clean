package com.alanaraujo.ficticiusclean.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alanaraujo.ficticiusclean.domain.model.ConsumoVeiculo;
import com.alanaraujo.ficticiusclean.domain.model.Veiculo;

@Service
public class RankingService {

	private CadastroVeiculoService cadastroVeiculoService;
	
	public RankingService(CadastroVeiculoService cadastroVeiculoService) {
		this.cadastroVeiculoService = cadastroVeiculoService;
	}
	
	public List<ConsumoVeiculo> calcularRanking(double valorCombustivel, int kmTotalCidade, int kmTotalRodovia) {
		
		List<Veiculo> veiculos = cadastroVeiculoService.listar();
		List<ConsumoVeiculo> ranking = new ArrayList<>();
		
		for(Veiculo veiculo : veiculos) {
			ranking.add(calculaConsumoVeiculo(veiculo,
					valorCombustivel, kmTotalCidade, kmTotalRodovia));	
		}
		
		ordenar(ranking);
		
		return ranking;
	}
		
	private ConsumoVeiculo calculaConsumoVeiculo(Veiculo veiculo,
            double valorCombustivel, double kmTotalCidade, double kmTotalRodovia) {
	
		double consumoCidade = kmTotalCidade / veiculo.getConsumoCidadeKmL();
        double consumoRodovia = kmTotalRodovia / veiculo.getConsumoRodoviaKmL();
        double consumoTotal = BigDecimal.valueOf(consumoCidade + consumoRodovia).setScale(2, RoundingMode.HALF_EVEN).doubleValue();                                     
        double valorTotal = BigDecimal.valueOf(consumoTotal * valorCombustivel).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        
        Calendar anoFabricacao = Calendar.getInstance();
        anoFabricacao.setTime(veiculo.getDataFabricacao());
        
        ConsumoVeiculo consumoVeiculo = new ConsumoVeiculo();
        consumoVeiculo.setNome(veiculo.getNome());
        consumoVeiculo.setMarca(veiculo.getModelo().getMarca().getNome());
        consumoVeiculo.setModelo(veiculo.getModelo().getNome());
        consumoVeiculo.setAno(anoFabricacao.get(Calendar.YEAR));
        consumoVeiculo.setConsumoCombustivel(consumoTotal);
        consumoVeiculo.setValorCombustivel(valorTotal);
        
        return consumoVeiculo;
	}
		
	private static void ordenar(List<ConsumoVeiculo> lista) {
		Collections.sort(lista, new Comparator<ConsumoVeiculo>() {
			@Override
			public int compare(ConsumoVeiculo veiculoA, ConsumoVeiculo veiculoB) {
				return Double.compare(veiculoA.getValorCombustivel(), veiculoB.getValorCombustivel());
			}
		});
	}
		
}
