package com.alanaraujo.ficticiusclean.api.controller;

import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeBADREQUEST;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeCREATED;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeNOCONTENT;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeNOTFOUND;
import static com.alanaraujo.ficticiusclean.api.controller.ResponseFactory.makeOK;
import static com.alanaraujo.ficticiusclean.api.mapper.VeiculoMapper.dtoParaEntidade;
import static com.alanaraujo.ficticiusclean.api.mapper.VeiculoMapper.entidadeParaDto;

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

import com.alanaraujo.ficticiusclean.api.dto.ConsumoVeiculoDto;
import com.alanaraujo.ficticiusclean.api.dto.RankingConsumo;
import com.alanaraujo.ficticiusclean.api.dto.SalvarVeiculoInput;
import com.alanaraujo.ficticiusclean.api.dto.SalvarVeiculoOutput;
import com.alanaraujo.ficticiusclean.api.mapper.ConsumoVeiculoMapper;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeNaoEncontradaException;
import com.alanaraujo.ficticiusclean.domain.model.ConsumoVeiculo;
import com.alanaraujo.ficticiusclean.domain.model.Veiculo;
import com.alanaraujo.ficticiusclean.domain.service.CadastroModeloService;
import com.alanaraujo.ficticiusclean.domain.service.CadastroVeiculoService;
import com.alanaraujo.ficticiusclean.domain.service.RankingService;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {

	CadastroVeiculoService cadastroVeiculoService;
	CadastroModeloService cadastroModeloService;
	RankingService rankingService;
	
	public VeiculoController(CadastroVeiculoService cadastroVeiculosService,
			CadastroModeloService cadastroModeloService, RankingService rankingService) {
		this.cadastroVeiculoService = cadastroVeiculosService;
		this.cadastroModeloService = cadastroModeloService;
		this.rankingService = rankingService;
	}
	
	@GetMapping
	public ResponseEntity listar() {
		
		List<Veiculo> veiculos = cadastroVeiculoService.listar();
		List<SalvarVeiculoOutput> veiculoOutput = new ArrayList<>();
		
		for(Veiculo veiculo : veiculos) {
			veiculoOutput.add(entidadeParaDto(veiculo));
		}
		
		return makeOK(veiculoOutput);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity buscar(@PathVariable Long id) {
		
		try {
			Veiculo veiculo = cadastroVeiculoService.buscar(id);
			return makeOK(entidadeParaDto(veiculo));
		} catch(EntidadeNaoEncontradaException e) {
			return makeNOTFOUND(e);
		} 
	}
	
	@GetMapping(value = "/ranking")
	public ResponseEntity<List<ConsumoVeiculoDto>> ranking(@Valid RankingConsumo rankingConsumo) {
		List<ConsumoVeiculo> ranking = rankingService.calcularRanking(
				rankingConsumo.getValorCombustivel(), rankingConsumo.getKmTotalCidade(),
				rankingConsumo.getKmTotalRodovia());
		
		List<ConsumoVeiculoDto> response = new ArrayList<>();
		
		for(ConsumoVeiculo consumo : ranking) {
			response.add(ConsumoVeiculoMapper.modelParaDto(consumo));
		}
		
		
		
		return makeOK(response);
	}
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody @Valid SalvarVeiculoInput veiculoInput) {
		
		Veiculo veiculo = dtoParaEntidade(veiculoInput);	
		
		try {
			veiculo = cadastroVeiculoService.salvar(veiculo);
			return makeCREATED(entidadeParaDto(veiculo));
		} catch(EntidadeNaoEncontradaException e) {
			return makeBADREQUEST(e);
		}
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity atualizar(@PathVariable Long id, @RequestBody SalvarVeiculoInput veiculoInput) {
		
		Veiculo veiculo = dtoParaEntidade(veiculoInput);		
		
		try {
			veiculo.setId(cadastroVeiculoService.buscar(id).getId());
			veiculo = cadastroVeiculoService.salvar(veiculo);
			return makeOK(entidadeParaDto(veiculo));
		} catch(EntidadeNaoEncontradaException e) {
			return makeBADREQUEST(e);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity remover(@PathVariable Long id) {
		try {
			cadastroVeiculoService.remover(id);
			return makeNOCONTENT();		
		} catch(EntidadeNaoEncontradaException e) {
			return makeNOTFOUND(e);
		}
	}
	
}
