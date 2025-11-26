package com.tec.producto.service;

import java.util.List;

import com.tec.producto.dto.CategoriaDto;

public interface CategoriaService {
	
	List<CategoriaDto>obtenerTodas();
	CategoriaDto obtenerPorId(Long id);
	CategoriaDto guardarCategoria(CategoriaDto categoriadto);
	CategoriaDto actualizarCategoria(Long id, CategoriaDto categoriadto);
	void eliminarCategoria(Long id);

}
