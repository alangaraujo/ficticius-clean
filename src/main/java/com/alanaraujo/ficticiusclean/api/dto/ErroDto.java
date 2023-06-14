package com.alanaraujo.ficticiusclean.api.dto;

public class ErroDto {

	private int statusHttp;
	private String mensagem;
	
	public ErroDto(int statusHttp, String mensagem) {
		this.statusHttp = statusHttp;
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getStatusHttp() {
		return statusHttp;
	}

	public void setStatusHttp(int statusHttp) {
		this.statusHttp = statusHttp;
	}

}
