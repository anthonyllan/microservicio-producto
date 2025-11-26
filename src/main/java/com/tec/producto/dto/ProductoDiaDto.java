package com.tec.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDiaDto {
	private Long id;
	private ProductoDto producto;
	private DiaSemanaDto dia;
	
}
