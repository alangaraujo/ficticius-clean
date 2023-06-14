package com.alanaraujo.ficticiusclean.domain.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.alanaraujo.ficticiusclean.domain.exception.EntidadeEmUsoException;
import com.alanaraujo.ficticiusclean.domain.exception.EntidadeNaoEncontradaException;
import com.alanaraujo.ficticiusclean.domain.model.Marca;
import com.alanaraujo.ficticiusclean.domain.repository.MarcaRepository;

@Service
public class CadastroMarcaService {

	private MarcaRepository marcaRepository;

	public CadastroMarcaService(MarcaRepository marcaRepository) {
		this.marcaRepository = marcaRepository;
	}
	
	public List<Marca> listar() {
		return marcaRepository.findAll();
	}
	
	public Marca buscar(Long id) {
		return marcaRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Marca não encontrada: " + id));
		
	}
	
	public Marca salvar(Marca marca) {
		try {
			return marcaRepository.save(marca);
		} catch (JpaObjectRetrievalFailureException e) {
			throw new EntidadeNaoEncontradaException(e.getCause().getMessage());
		}
	}
	
	public void remover(Long id) {
		try {
			marcaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
						String.format("Marca de código %d não pode ser removida, pois está em uso", id));
			
		} catch (EmptyResultDataAccessException e){
			throw new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de marca com código %d", id));
		}
	}
	
}
