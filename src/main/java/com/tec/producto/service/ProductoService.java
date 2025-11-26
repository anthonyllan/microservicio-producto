package com.tec.producto.service;

import java.util.List;

import com.tec.producto.dto.ProductoDto;
import com.tec.producto.dto.ProductoConDiasDto;

public interface ProductoService {
    List<ProductoDto> buscarTodos();
    ProductoDto buscarPorId(Long id);
    ProductoDto guardarProducto(ProductoDto productoDto);
    ProductoDto actualizarProducto(Long id, ProductoDto productoDto);
    void eliminarProducto(Long id);

    // Nuevo método para crear producto + días
    ProductoDto guardarProductoConDias(ProductoConDiasDto productoConDiasDto);
}