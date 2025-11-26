package com.tec.producto.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tec.producto.dto.ProductoDiaDto;
import com.tec.producto.entity.ProductoDia;
import com.tec.producto.entity.Producto;
import com.tec.producto.entity.DiaSemana;
import com.tec.producto.exception.ResourceNotFoundException;
import com.tec.producto.mapper.ProductoDiaMapper;
import com.tec.producto.repository.ProductoDiaRepository;
import com.tec.producto.repository.ProductoRepository;
import com.tec.producto.repository.DiaSemanaRepository;
import com.tec.producto.service.ProductoDiaService;

@Service
public class ProductoDiaServiceImpl implements ProductoDiaService {

    @Autowired
    private ProductoDiaRepository productoDiaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private DiaSemanaRepository diaSemanaRepository;

    @Override
    public ProductoDiaDto guardarProductoDia(ProductoDiaDto productoDiaDto) {
        ProductoDia productoDia = ProductoDiaMapper.mapToProductoDia(productoDiaDto);

        // Buscar y asignar Producto
        Long productoId = productoDiaDto.getProducto().getId();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + productoId));
        productoDia.setProducto(producto);

        // Buscar y asignar DiaSemana
        Long diaId = productoDiaDto.getDia().getId();
        DiaSemana diaSemana = diaSemanaRepository.findById(diaId)
                .orElseThrow(() -> new ResourceNotFoundException("Dia de la semana no encontrado: " + diaId));
        productoDia.setDiaSemana(diaSemana);

        ProductoDia savedProductoDia = productoDiaRepository.save(productoDia);
        return ProductoDiaMapper.mapToProductoDiaDto(savedProductoDia);
    }

    @Override
    public ProductoDiaDto buscarPorId(Long id) {
        ProductoDia productoDia = productoDiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductoDia no encontrado: " + id));
        return ProductoDiaMapper.mapToProductoDiaDto(productoDia);
    }

    @Override
    public List<ProductoDiaDto> buscarTodos() {
        List<ProductoDia> productoDias = productoDiaRepository.findAll();
        return productoDias.stream()
                .map(ProductoDiaMapper::mapToProductoDiaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDiaDto actualizarProductoDia(Long id, ProductoDiaDto productoDiaDto) {
        ProductoDia productoDiaExistente = productoDiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductoDia no encontrado: " + id));

        // Actualizar Producto
        Long productoId = productoDiaDto.getProducto().getId();
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + productoId));
        productoDiaExistente.setProducto(producto);

        // Actualizar DiaSemana
        Long diaId = productoDiaDto.getDia().getId();
        DiaSemana diaSemana = diaSemanaRepository.findById(diaId)
                .orElseThrow(() -> new ResourceNotFoundException("DiaSemana no encontrado: " + diaId));
        productoDiaExistente.setDiaSemana(diaSemana);

        ProductoDia productoDiaActualizado = productoDiaRepository.save(productoDiaExistente);
        return ProductoDiaMapper.mapToProductoDiaDto(productoDiaActualizado);
    }

    @Override
    public void eliminarProductoDia(Long id) {
        ProductoDia productoDia = productoDiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductoDia no encontrado: " + id));
        productoDiaRepository.delete(productoDia);
    }
    
    @Override
    public List<ProductoDiaDto> buscarPorProducto(Long productoId) {
        List<ProductoDia> productoDias = productoDiaRepository.findByProductoId(productoId);
        return productoDias.stream()
                .map(ProductoDiaMapper::mapToProductoDiaDto)
                .collect(Collectors.toList());
    }
}