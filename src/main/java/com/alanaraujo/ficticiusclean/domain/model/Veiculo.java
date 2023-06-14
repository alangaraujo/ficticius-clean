package com.alanaraujo.ficticiusclean.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table
public class Veiculo {

	@Id
	@ApiModelProperty(position = 0)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	@ApiModelProperty(position = 1)
	private String nome;
	
	@Column(nullable = false)
	@ApiModelProperty(position = 2)
	@Temporal(TemporalType.DATE)
	private Date dataFabricacao;
	
	@Column(nullable = false)
	@ApiModelProperty(position = 3)
    private int consumoCidadeKmL;
    
	@Column(nullable = false)
	@ApiModelProperty(position = 4)
    private int consumoRodoviaKmL;
	
	@ManyToOne
	@ApiModelProperty(position = 5)
	@JoinColumn(name = "modelo_id", nullable = false)
	private Modelo modelo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataFabricacao() {
		return dataFabricacao;
	}

	public void setDataFabricacao(Date dataFabricacao) {
		this.dataFabricacao = dataFabricacao;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public int getConsumoCidadeKmL() {
		return consumoCidadeKmL;
	}

	public void setConsumoCidadeKmL(int consumoCidadeKmL) {
		this.consumoCidadeKmL = consumoCidadeKmL;
	}

	public int getConsumoRodoviaKmL() {
		return consumoRodoviaKmL;
	}

	public void setConsumoRodoviaKmL(int consumoRodoviaKmL) {
		this.consumoRodoviaKmL = consumoRodoviaKmL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		
	
}
