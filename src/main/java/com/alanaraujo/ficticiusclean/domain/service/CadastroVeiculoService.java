package com.alanaraujo.ficticiusclean.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.alanaraujo.ficticiusclean.domain.exception.EntidadeEmUsoException;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeNaoEncontradaException;
import com.alanaraujo.ficticiusclean.domain.model.Veiculo;
import com.alanaraujo.ficticiusclean.domain.repository.VeiculoRepository;

@Service
public class CadastroVeiculoService {

	private VeiculoRepository veiculoRepository;

	public CadastroVeiculoService(VeiculoRepository veiculoRepository) {
		this.veiculoRepository = veiculoRepository;
	}
	
	public List<Veiculo> listar() {
		return veiculoRepository.findAll();
	}
	
	public Veiculo buscar(Long id) {
		return veiculoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Veículo não encontrado: " + id));
	}
	
	public Veiculo salvar(Veiculo veiculo) {
		try {
			return veiculoRepository.save(veiculo);
		} catch (JpaObjectRetrievalFailureException e) {
			throw new EntidadeNaoEncontradaException(e.getCause().getMessage());
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeNaoEncontradaException("Marca com Id informada não existe.");
		}
	}
	
	public void remover(Long id) {
		try {
			veiculoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
						String.format("Veículo de código %d não pode ser removido, pois está em uso", id));
			
		} catch (EmptyResultDataAccessException e){
			throw new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de veículo com código %d", id));
		}
	}
}
