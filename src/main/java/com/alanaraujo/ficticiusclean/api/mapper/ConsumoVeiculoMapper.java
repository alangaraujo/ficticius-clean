package com.alanaraujo.ficticiusclean.api.mapper;

import org.modelmapper.ModelMapper;

import com.alanaraujo.ficticiusclean.api.dto.ConsumoVeiculoDto;
import com.alanaraujo.ficticiusclean.domain.model.ConsumoVeiculo;

public class ConsumoVeiculoMapper {
	
	private static final ModelMapper MAPPER = new ModelMapper();
	
	public static ConsumoVeiculoDto modelParaDto(ConsumoVeiculo consumoVeiculo) {
		return MAPPER.map(consumoVeiculo, ConsumoVeiculoDto.class);
	}
}
