package com.tec.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoConDiasDto {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Boolean disponible;
    private String imagen;
    private Integer tiempoPreparacion;
    private Long idCategoria; 
    private List<Long> diasDisponibles; // IDs de los días (lunes, martes, ...)
}