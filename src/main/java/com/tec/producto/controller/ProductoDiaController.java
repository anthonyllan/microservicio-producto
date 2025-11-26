package com.tec.producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec.producto.dto.ProductoDiaDto;
import com.tec.producto.service.ProductoDiaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/productodia")
public class ProductoDiaController {

    @Autowired
    private ProductoDiaService productoDiaService;
    
    

    @PostMapping
    public ResponseEntity<ProductoDiaDto> crearProductoDia(@RequestBody ProductoDiaDto productoDiaDto) {
        try {
            ProductoDiaDto saveProductoDia = productoDiaService.guardarProductoDia(productoDiaDto);
            return new ResponseEntity<>(saveProductoDia, HttpStatus.CREATED);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDiaDto> getProductoDiaById(@PathVariable("id") Long productoDiaId) {
        ProductoDiaDto productoDiaDto = productoDiaService.buscarPorId(productoDiaId);
        return ResponseEntity.ok(productoDiaDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDiaDto>> getAllProductoDias() {
        List<ProductoDiaDto> productoDias = productoDiaService.buscarTodos();
        return ResponseEntity.ok(productoDias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDiaDto> updateProductoDia(@PathVariable("id") Long productoDiaId, @RequestBody ProductoDiaDto actualizarProductoDia) {
        ProductoDiaDto updatedProductoDia = productoDiaService.actualizarProductoDia(productoDiaId, actualizarProductoDia);
        return ResponseEntity.ok(updatedProductoDia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductoDia(@PathVariable("id") Long productoDiaId) {
        productoDiaService.eliminarProductoDia(productoDiaId);
        return ResponseEntity.ok("ProductoDia eliminado correctamente");
    }
    
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ProductoDiaDto>> getProductoDiasByProducto(@PathVariable("productoId") Long productoId) {
        List<ProductoDiaDto> productoDias = productoDiaService.buscarPorProducto(productoId);
        return ResponseEntity.ok(productoDias);
    }
}