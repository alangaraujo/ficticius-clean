package com.alanaraujo.ficticiusclean.domain.exception;

public class EntidadeEmUsoException extends RuntimeException {

	private static final long serialVersionUID = -7464075564821019825L;
	
	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

}
