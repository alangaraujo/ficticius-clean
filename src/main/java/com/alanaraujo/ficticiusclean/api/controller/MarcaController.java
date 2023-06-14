package com.alanaraujo.ficticiusclean.api.controller;

import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeCONFLICT;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeCREATED;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeNOCONTENT;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeNOTFOUND;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeOK;
import static com.alanaraujo.ficticiusclean.api.mapper.MarcaMapper.dtoParaEntidade;
import static com.alanaraujo.ficticiusclean.api.mapper.MarcaMapper.entidadeParaDto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alanaraujo.ficticiusclean.api.dto.SalvarMarcaInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarMarcaOutput;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeEmUsoException;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeNaoEncontradaException;
import com.alanaraujo.ficticiusclean.domain.model.Marca;
import com.alanaraujo.ficticiusclean.domain.service.CadastroMarcaService;

@RestController
@RequestMapping(path = "/marcas")
public class MarcaController {

	CadastroMarcaService cadastroMarcaService;

	public MarcaController(CadastroMarcaService cadastroMarcaService) {
		
		this.cadastroMarcaService = cadastroMarcaService;
	}
	
	@GetMapping	
	public ResponseEntity listar() {
		
		List<Marca> marcas = cadastroMarcaService.listar();
		List<SalvarMarcaOutput> marcaOutput = new ArrayList<>();
		
		for(Marca marca : marcas) {
			marcaOutput.add(entidadeParaDto(marca));
		}
		
		return makeOK(marcaOutput);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity buscar(@PathVariable Long id) {
		
		try {
			Marca marca = cadastroMarcaService.buscar(id);
			return makeOK(entidadeParaDto(marca));
		} catch(EntidadeNaoEncontradaException e) {
			return makeNOTFOUND(e);
		}
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody @Valid SalvarMarcaInput marcaInput) {
		
		Marca marca = dtoParaEntidade(marcaInput);
		
		marca = cadastroMarcaService.salvar(marca);
		return makeCREATED(entidadeParaDto(marca));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity atualizar(@PathVariable(value = "id") Long id, @RequestBody SalvarMarcaInput marcaInput) {
		
		Marca marca = dtoParaEntidade(marcaInput);
		marca.setId(id);
		
		try {
			Marca marcaAtual = cadastroMarcaService.buscar(id);
			BeanUtils.copyProperties(marca, marcaAtual);
			marca = cadastroMarcaService.salvar(marcaAtual);
			return makeOK(entidadeParaDto(marca));
		} catch (EntidadeNaoEncontradaException e) {
			return makeNOTFOUND(e);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity remover(@PathVariable Long id) {	
		
		try {			
			cadastroMarcaService.remover(id);
			return makeNOCONTENT();
		} catch (EntidadeEmUsoException e) {
			return makeCONFLICT(e);			
		} catch (EntidadeNaoEncontradaException e) {
			return makeNOTFOUND(e);
		}
	}
	
}
