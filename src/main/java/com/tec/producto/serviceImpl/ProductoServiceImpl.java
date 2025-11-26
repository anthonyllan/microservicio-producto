package com.tec.producto.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec.producto.dto.ProductoDto;
import com.tec.producto.dto.ProductoConDiasDto;
import com.tec.producto.entity.Producto;
import com.tec.producto.entity.Categoria;
import com.tec.producto.entity.DiaSemana;
import com.tec.producto.entity.ProductoDia;
import com.tec.producto.exception.ResourceNotFoundException;
import com.tec.producto.mapper.ProductoMapper;
import com.tec.producto.repository.ProductoRepository;
import com.tec.producto.repository.CategoriaRepository;
import com.tec.producto.repository.DiaSemanaRepository;
import com.tec.producto.repository.ProductoDiaRepository;
import com.tec.producto.service.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private DiaSemanaRepository diaSemanaRepository;

    @Autowired
    private ProductoDiaRepository productoDiaRepository;

    @Override
    public ProductoDto guardarProducto(ProductoDto productoDto) {
        Producto producto = ProductoMapper.mapToProducto(productoDto);

        // Buscar la categoria en la base de datos y asignarla al producto
        if (productoDto.getCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(productoDto.getCategoria().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada " + productoDto.getCategoria().getId()));
            producto.setCategoria(categoria);
        }

        Producto nuevoProducto = productoRepository.save(producto);
        return ProductoMapper.mapToProductoDto(nuevoProducto);
    }

    @Override
    public ProductoDto guardarProductoConDias(ProductoConDiasDto dto) {
        // 1. Guardar el producto
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setDisponible(dto.getDisponible());
        producto.setImagen(dto.getImagen());
        producto.setTiempoPreparacion(dto.getTiempoPreparacion());

        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
            .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada"));
        producto.setCategoria(categoria);

        Producto productoGuardado = productoRepository.save(producto);

        // 2. Asociar días
        for(Long idDia : dto.getDiasDisponibles()) {
            DiaSemana dia = diaSemanaRepository.findById(idDia)
                .orElseThrow(() -> new ResourceNotFoundException("Día no encontrado: " + idDia));
            ProductoDia pd = new ProductoDia();
            pd.setProducto(productoGuardado);
            pd.setDiaSemana(dia);
            productoDiaRepository.save(pd);
        }

        return ProductoMapper.mapToProductoDto(productoGuardado);
    }

    @Override
    public ProductoDto buscarPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con el ID: " + id + " no encontrado."));
        return ProductoMapper.mapToProductoDto(producto);
    }

    @Override
    public List<ProductoDto> buscarTodos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(ProductoMapper::mapToProductoDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDto actualizarProducto(Long id, ProductoDto productoDto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con el ID: " + id + " no encontrado."));

        productoExistente.setNombre(productoDto.getNombre());
        productoExistente.setDescripcion(productoDto.getDescripcion());
        productoExistente.setPrecio(productoDto.getPrecio());
        productoExistente.setDisponible(productoDto.getDisponible());
        productoExistente.setImagen(productoDto.getImagen());
        productoExistente.setTiempoPreparacion(productoDto.getTiempoPreparacion());

        // Actualizar la categoria si viene en el DTO
        if (productoDto.getCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(productoDto.getCategoria().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada " + productoDto.getCategoria().getId()));
            productoExistente.setCategoria(categoria);
        }

        Producto productoActualizado = productoRepository.save(productoExistente);
        return ProductoMapper.mapToProductoDto(productoActualizado);
    }

    @Override
    public void eliminarProducto(Long id) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto con el ID: " + id + " no encontrado."));
        productoRepository.delete(productoExistente);
    }
}