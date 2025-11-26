package com.tec.producto.mapper;

import com.tec.producto.dto.ProductoDiaDto;
import com.tec.producto.entity.ProductoDia;

public class ProductoDiaMapper {
    public static ProductoDiaDto mapToProductoDiaDto(ProductoDia productoDia) {
        if (productoDia == null) {
            return null;
        } else {
            return new ProductoDiaDto(
                productoDia.getId(),
                ProductoMapper.mapToProductoDto(productoDia.getProducto()),
                DiaSemanaMapper.mapToDiaSemanaDto(productoDia.getDiaSemana())
            );
        }
    }

    public static ProductoDia mapToProductoDia(ProductoDiaDto productoDiaDto) {
        if (productoDiaDto == null) {
            return null;
        } else {
            return new ProductoDia(
                productoDiaDto.getId(),
                ProductoMapper.mapToProducto(productoDiaDto.getProducto()),
                DiaSemanaMapper.mapToDiaSemana(productoDiaDto.getDia())
            );
        }
    }
}