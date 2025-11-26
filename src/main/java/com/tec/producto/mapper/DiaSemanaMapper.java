package com.tec.producto.mapper;

import com.tec.producto.dto.DiaSemanaDto;
import com.tec.producto.entity.DiaSemana;

public class DiaSemanaMapper {
	public static DiaSemanaDto mapToDiaSemanaDto(DiaSemana diasemana) {
		if (diasemana == null) {
			return null;
		} else {
			return new DiaSemanaDto(
					diasemana.getId(),
					diasemana.getNombre(),
					diasemana.getHabilitado()
					);
		}
	}
	public static DiaSemana mapToDiaSemana(DiaSemanaDto diasemanadto) {
		if (diasemanadto == null) {
			return null;
		} else {
			return new DiaSemana(
					diasemanadto.getId(),
					diasemanadto.getNombre(),
					diasemanadto.getHabilitado()
					);
		}
	}
}
