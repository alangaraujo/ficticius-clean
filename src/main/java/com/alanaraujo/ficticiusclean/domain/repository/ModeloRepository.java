package com.alanaraujo.ficticiusclean.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alanaraujo.ficticiusclean.domain.model.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
	
}
