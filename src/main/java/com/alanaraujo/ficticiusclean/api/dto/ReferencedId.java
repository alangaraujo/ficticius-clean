package com.alanaraujo.ficticiusclean.api.dto;

public class ReferencedId<T> {
	
	private T id;
	
	public ReferencedId() {}

	public ReferencedId(T id) {
		this.id = id;
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

}
