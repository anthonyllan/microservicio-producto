package com.tec.producto.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tec.producto.dto.CategoriaDto;
import com.tec.producto.entity.Categoria;
import com.tec.producto.exception.ResourceNotFoundException;
import com.tec.producto.mapper.CategoriaMapper;
import com.tec.producto.repository.CategoriaRepository;
import com.tec.producto.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaDto> obtenerTodas() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(CategoriaMapper::mapToCategoriaDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDto obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con el ID: " + id + " no encontrada."));
        return CategoriaMapper.mapToCategoriaDto(categoria);
    }

    @Override
    public CategoriaDto guardarCategoria(CategoriaDto categoriadto) {
        Categoria categoria = CategoriaMapper.mapToCategoria(categoriadto);
        Categoria newCategoria = categoriaRepository.save(categoria);
        return CategoriaMapper.mapToCategoriaDto(newCategoria);
    }

    @Override
    public CategoriaDto actualizarCategoria(Long id, CategoriaDto categoriadto) {
        Categoria existeCategoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con el ID: " + id + " no encontrada."));
        existeCategoria.setNombre(categoriadto.getNombre());
        existeCategoria.setDescripcion(categoriadto.getDescripcion());
        existeCategoria.setImagen(categoriadto.getImagen());
        Categoria actualizarCategoria = categoriaRepository.save(existeCategoria);
        return CategoriaMapper.mapToCategoriaDto(actualizarCategoria);
    }

    @Override
    public void eliminarCategoria(Long id) {
        Categoria existeCategoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria con el ID: " + id + " no encontrada."));
        categoriaRepository.delete(existeCategoria);
    }
}