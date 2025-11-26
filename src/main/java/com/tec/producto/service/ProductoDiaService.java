package com.tec.producto.service;

import java.util.List;

import com.tec.producto.dto.ProductoDiaDto;

public interface ProductoDiaService {
    ProductoDiaDto guardarProductoDia(ProductoDiaDto productoDiaDto);
    ProductoDiaDto buscarPorId(Long id);
    List<ProductoDiaDto> buscarTodos();
    ProductoDiaDto actualizarProductoDia(Long id, ProductoDiaDto productoDiaDto);
    void eliminarProductoDia(Long id);
    List<ProductoDiaDto> buscarPorProducto(Long productoId);
    
}