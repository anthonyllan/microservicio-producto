package com.tec.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaSemanaDto {
	private Long id;
	private String nombre;
	private Boolean habilitado;
}
