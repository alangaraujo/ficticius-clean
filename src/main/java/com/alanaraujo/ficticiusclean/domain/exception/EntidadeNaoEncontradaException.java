package com.alanaraujo.ficticiusclean.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = -4333095533850707182L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
}
