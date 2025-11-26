package com.tec.producto.mapper;

import com.tec.producto.dto.ProductoDto;
import com.tec.producto.entity.Producto;

public class ProductoMapper {
	public static ProductoDto mapToProductoDto(Producto producto) {
		if (producto == null) {
			return null;
		} else {
			return new ProductoDto(
					producto.getId(),
					producto.getNombre(),
					producto.getDescripcion(),
					producto.getPrecio(),
					producto.getDisponible(),
					producto.getImagen(),
					producto.getTiempoPreparacion(),
					CategoriaMapper.mapToCategoriaDto(producto.getCategoria())
					);
		}
	}
	public static Producto mapToProducto(ProductoDto productodto) {
		if (productodto == null) {
			return null;
		} else {
			return new Producto(
					productodto.getId(),
					productodto.getNombre(),
					productodto.getDescripcion(),
					productodto.getPrecio(),
					productodto.getDisponible(),
					productodto.getImagen(),
					productodto.getTiempoPreparacion(),
					CategoriaMapper.mapToCategoria(productodto.getCategoria())
					);
		}
	}
}
