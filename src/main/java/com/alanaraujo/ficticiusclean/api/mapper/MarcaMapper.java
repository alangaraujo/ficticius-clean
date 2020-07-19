package com.alanaraujo.ficticiusclean.api.mapper;

import org.modelmapper.ModelMapper;

import com.alanaraujo.ficticiusclean.api.dto.SalvarMarcaInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarMarcaOutput;
import com.alanaraujo.ficticiusclean.domain.model.Marca;

public class MarcaMapper {
	
	private static final ModelMapper MAPPER = new ModelMapper();
	
	public static Marca dtoParaEntidade(SalvarMarcaInput marcaInput) {
		return MAPPER.map(marcaInput, Marca.class);
	}
	
	public static SalvarMarcaOutput entidadeParaDto(Marca marca) {
		return MAPPER.map(marca, SalvarMarcaOutput.class);
	}
	
}
