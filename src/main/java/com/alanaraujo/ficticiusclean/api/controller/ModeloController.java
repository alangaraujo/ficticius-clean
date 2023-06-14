package com.alanaraujo.ficticiusclean.api.controller;

import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeBADREQUEST;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeCONFLICT;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeCREATED;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeNOCONTENT;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeNOTFOUND;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeOK;
import static com.alanaraujo.ficticiusclean.api.mapper.ModeloMapper.dtoParaEntidade;
import static com.alanaraujo.ficticiusclean.api.mapper.ModeloMapper.entidadeParaDto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alanaraujo.ficticiusclean.api.dto.SalvarModeloInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarModeloOutput;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeEmUsoException;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeNaoEncontradaException;
import com.alanaraujo.ficticiusclean.domain.model.Modelo;
import com.alanaraujo.ficticiusclean.domain.service.CadastroMarcaService;
import com.alanaraujo.ficticiusclean.domain.service.CadastroModeloService;

@RestController
@RequestMapping(value = "/modelos")
public class ModeloController {

	CadastroModeloService cadastroModeloService;
	CadastroMarcaService cadastroMarcaService;

	public ModeloController(CadastroModeloService cadastroModeloService, CadastroMarcaService cadastroMarcaService) {

		this.cadastroModeloService = cadastroModeloService;
		this.cadastroMarcaService = cadastroMarcaService;
	}

	@GetMapping
	public ResponseEntity listar() {
		
		List<Modelo> modelos = cadastroModeloService.listar();
		List<SalvarModeloOutput> modeloOutput = new ArrayList<>();
		
		for(Modelo modelo : modelos) {
			modeloOutput.add(entidadeParaDto(modelo));
		}
		
		return makeOK(modeloOutput);
		
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity buscar(@PathVariable Long id) {

		try {
			Modelo modelo = cadastroModeloService.buscar(id);
			return makeOK(entidadeParaDto(modelo));
		} catch (EntidadeNaoEncontradaException e) {
			return makeNOTFOUND(e);
		}
	}

	@PostMapping
	public ResponseEntity salvar(@RequestBody @Valid SalvarModeloInput modeloInput) {

		Modelo modelo = dtoParaEntidade(modeloInput);

		try {
			modelo = cadastroModeloService.salvar(modelo);
			return makeCREATED(entidadeParaDto(modelo));
		} catch (EntidadeNaoEncontradaException e) {
			return makeBADREQUEST(e);
		} 
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity atualizar(@PathVariable(value = "id") Long id, @RequestBody SalvarModeloInput modeloInput) {

		Modelo modelo = dtoParaEntidade(modeloInput);

		try {
			modelo.setId(cadastroModeloService.buscar(id).getId());
			modelo = cadastroModeloService.salvar(modelo);
			return makeOK(entidadeParaDto(modelo));
		} catch (EntidadeNaoEncontradaException e) {
			return makeBADREQUEST(e);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity remover(@PathVariable Long id) {

		try {
			cadastroModeloService.remover(id);
			return makeNOCONTENT();
		} catch (EntidadeNaoEncontradaException e) {
			return makeNOTFOUND(e);
		} catch (EntidadeEmUsoException e) {
			return makeCONFLICT(e);
		}
	}

}