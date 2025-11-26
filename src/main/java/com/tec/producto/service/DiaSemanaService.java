package com.tec.producto.service;

import java.util.List;

import com.tec.producto.dto.DiaSemanaDto;

public interface DiaSemanaService {
	List<DiaSemanaDto> buscarTodos();
	DiaSemanaDto buscarPorId(Long id);
	DiaSemanaDto cambiarEstadoDia(Long id, Boolean habilitado);
	List<DiaSemanaDto> buscarHabilitados();
}
