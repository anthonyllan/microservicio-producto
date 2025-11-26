package com.tec.producto.mapper;

import com.tec.producto.dto.CategoriaDto;
import com.tec.producto.entity.Categoria;

public class CategoriaMapper {
	
	public static CategoriaDto mapToCategoriaDto(Categoria categoria) {
		if (categoria == null) {
			return null;
		} else {
			return new CategoriaDto(
					categoria.getId(),
					categoria.getNombre(),
					categoria.getDescripcion(),
					categoria.getImagen()
					);
		}
		
	}
	
	public static Categoria mapToCategoria(CategoriaDto categoriadto) {
		if (categoriadto == null) {
			return null;
		} else {
			return new Categoria(
					categoriadto.getId(),
					categoriadto.getNombre(),
					categoriadto.getDescripcion(),
					categoriadto.getImagen()
					);
		}
	}
	
}
