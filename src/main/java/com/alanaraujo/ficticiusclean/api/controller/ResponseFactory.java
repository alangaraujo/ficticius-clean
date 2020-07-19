package com.alanaraujo.ficticiusclean.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.alanaraujo.ficticiusclean.api.dto.ErroDto;

public final class ResponseFactory {

	private ResponseFactory() {}
	
	public static <T> ResponseEntity<T> makeOK(T body) {
		return ResponseEntity.ok(body);
	}

	public static <T> ResponseEntity<T> makeNOCONTENT() {
		return ResponseEntity.noContent().build();
	}
	
	public static <T> ResponseEntity<T> makeCREATED(T body) {
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}
	
	public static <T extends Throwable> ResponseEntity makeBADREQUEST(T t) {
		ErroDto erro = new ErroDto(HttpStatus.BAD_REQUEST.value(), t.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	public static <T extends Throwable> ResponseEntity makeCONFLICT(T t) {
		ErroDto erro = new ErroDto(HttpStatus.CONFLICT.value(), t.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	public static <T extends Throwable> ResponseEntity makeNOTFOUND(T t) {
		ErroDto erro = new ErroDto(HttpStatus.NOT_FOUND.value(), t.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
}