package com.tec.producto.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tec.producto.dto.DiaSemanaDto;
import com.tec.producto.entity.DiaSemana;
import com.tec.producto.exception.ResourceNotFoundException;
import com.tec.producto.mapper.DiaSemanaMapper;
import com.tec.producto.repository.DiaSemanaRepository;
import com.tec.producto.service.DiaSemanaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaSemanaServiceImpl implements DiaSemanaService {

    private final DiaSemanaRepository diaSemanaRepository;

    @Override
    public List<DiaSemanaDto> buscarTodos() {
        List<DiaSemana> diasSemana = diaSemanaRepository.findAll(); 
        return diasSemana.stream().map(DiaSemanaMapper::mapToDiaSemanaDto).collect(Collectors.toList());
    }

    @Override
    public DiaSemanaDto buscarPorId(Long id) {
        DiaSemana diasemana = diaSemanaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Día con el ID: " + id + " no encontrada."));
        return DiaSemanaMapper.mapToDiaSemanaDto(diasemana);
    }

    @Override
    public DiaSemanaDto cambiarEstadoDia(Long id, Boolean habilitado) {
        DiaSemana existeDia = diaSemanaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Día con el ID: " + id + " no encontrada."));
        
        // Solo cambiar el estado habilitado
        existeDia.setHabilitado(habilitado);
        
        DiaSemana diaActualizado = diaSemanaRepository.save(existeDia);
        return DiaSemanaMapper.mapToDiaSemanaDto(diaActualizado);
    }
    
    @Override
    public List<DiaSemanaDto> buscarHabilitados() {
        // Buscar días donde habilitado = 0x01 (true)
        List<DiaSemana> diasHabilitados = diaSemanaRepository.findByHabilitadoTrue();
        return diasHabilitados.stream()
                .map(DiaSemanaMapper::mapToDiaSemanaDto)
                .collect(Collectors.toList());
    }
}