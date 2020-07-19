package com.alanaraujo.ficticiusclean.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alanaraujo.ficticiusclean.domain.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
	
}
