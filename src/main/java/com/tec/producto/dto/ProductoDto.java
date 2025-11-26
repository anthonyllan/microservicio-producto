package com.tec.producto.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {

	private Long id;
	private String nombre;
	private String descripcion;
	private Double precio;
	private Boolean disponible;
	private String imagen;
	private Integer tiempoPreparacion;
	private CategoriaDto categoria;
	
}
