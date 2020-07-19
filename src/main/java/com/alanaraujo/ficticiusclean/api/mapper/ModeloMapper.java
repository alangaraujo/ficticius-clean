package com.alanaraujo.ficticiusclean.api.mapper;

import org.modelmapper.ModelMapper;

import com.alanaraujo.ficticiusclean.api.dto.SalvarModeloInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarModeloOutput;
import com.alanaraujo.ficticiusclean.domain.model.Modelo;

public class ModeloMapper {
	
	private static final ModelMapper MAPPER = new ModelMapper();
	
	public static Modelo dtoParaEntidade(SalvarModeloInput modeloInput) {
		return MAPPER.map(modeloInput, Modelo.class);
	}
	
	public static SalvarModeloOutput entidadeParaDto(Modelo modelo) {
		return MAPPER.map(modelo, SalvarModeloOutput.class);
	}
	
}
