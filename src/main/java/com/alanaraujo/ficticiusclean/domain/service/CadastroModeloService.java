package com.alanaraujo.ficticiusclean.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.alanaraujo.ficticiusclean.domain.exception.EntidadeEmUsoException;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeNaoEncontradaException;
import com.alanaraujo.ficticiusclean.domain.model.Modelo;
import com.alanaraujo.ficticiusclean.domain.repository.ModeloRepository;

@Service
public class CadastroModeloService {

	private ModeloRepository modeloRepository;

	public CadastroModeloService(ModeloRepository modeloRepository) {
		this.modeloRepository = modeloRepository;
	}
	
	public List<Modelo> listar() {
		return modeloRepository.findAll();
	}
	
	public Modelo buscar(Long id) {
		return modeloRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Modelo não encontrado: " + id));
	}
	

	public Modelo salvar(Modelo modelo) {	
		try {
			return modeloRepository.save(modelo);
		} catch (JpaObjectRetrievalFailureException e) {
			throw new EntidadeNaoEncontradaException(e.getCause().getMessage());
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeNaoEncontradaException("Marca com Id informada Não existe.");
		}
	}
	
	public void remover(Long id) {
		try {
			modeloRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
						String.format("Modelo de código %d não pode ser removido, pois está em uso", id));
			
		} catch (EmptyResultDataAccessException e){
			throw new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de modelo com código %d", id));
		}
	}
}
