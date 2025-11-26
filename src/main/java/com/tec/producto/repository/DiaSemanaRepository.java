package com.tec.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tec.producto.entity.DiaSemana;

@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Long>{
	List<DiaSemana> findByHabilitadoTrue();
}
