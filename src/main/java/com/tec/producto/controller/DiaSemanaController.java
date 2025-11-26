package com.tec.producto.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec.producto.dto.DiaSemanaDto;
import com.tec.producto.service.DiaSemanaService;

@RestController
@RequestMapping("/api/dia")
public class DiaSemanaController {
	
	@Autowired
	private DiaSemanaService diaSemanaService;
	
	@GetMapping
    public List<DiaSemanaDto> obtenerTodos() {
        return diaSemanaService.buscarTodos();
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<DiaSemanaDto> obtenerPorId(@PathVariable Long id) {
        DiaSemanaDto diaSemanaDto = diaSemanaService.buscarPorId(id);
        return ResponseEntity.ok(diaSemanaDto);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<DiaSemanaDto> cambiarEstadoDia(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        Boolean habilitado = request.get("habilitado");
        DiaSemanaDto diaActualizado = diaSemanaService.cambiarEstadoDia(id, habilitado);
        return ResponseEntity.ok(diaActualizado);
    }
    
    @GetMapping("/habilitados")
    public ResponseEntity<List<DiaSemanaDto>> getDiasHabilitados() {
        List<DiaSemanaDto> diasHabilitados = diaSemanaService.buscarHabilitados();
        return ResponseEntity.ok(diasHabilitados);
    }
    
}