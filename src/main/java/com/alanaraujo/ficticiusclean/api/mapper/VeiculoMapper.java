package com.alanaraujo.ficticiusclean.api.mapper;

import org.modelmapper.ModelMapper;

import com.alanaraujo.ficticiusclean.api.dto.SalvarVeiculoInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarVeiculoOutput;
import com.alanaraujo.ficticiusclean.domain.model.Veiculo;

public class VeiculoMapper {
	
	private static final ModelMapper MAPPER = new ModelMapper();
	
	public static Veiculo dtoParaEntidade(SalvarVeiculoInput veiculoInput) {
		return MAPPER.map(veiculoInput, Veiculo.class);
	}
	
	public static SalvarVeiculoOutput entidadeParaDto(Veiculo veiculo) {
		return MAPPER.map(veiculo, SalvarVeiculoOutput.class);
	}
	
}
