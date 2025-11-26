package com.tec.producto.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tec.producto.dto.ProductoConDiasDto;
import com.tec.producto.dto.ProductoDto;
import com.tec.producto.service.ProductoService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    
    @Value("${app.upload.dir:/uploads/categorias}")
    private String baseUploadDir;

    @PostMapping("/con-dias")
    public ResponseEntity<ProductoDto> crearProductoConDias(@RequestBody ProductoConDiasDto productoConDiasDto) {
        ProductoDto productoCreado = productoService.guardarProductoConDias(productoConDiasDto);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ProductoDto> crearProducto(@RequestBody ProductoDto productoDto) {
        ProductoDto saveProducto = productoService.guardarProducto(productoDto);
        return new ResponseEntity<>(saveProducto, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductoDto> getProductoById(@PathVariable("id") Long productoId) {
        ProductoDto productoDto = productoService.buscarPorId(productoId);
        return ResponseEntity.ok(productoDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductoDto>> getAllProductos() {
        List<ProductoDto> productos = productoService.buscarTodos();
        return ResponseEntity.ok(productos);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductoDto> updateProducto(@PathVariable("id") Long productoId, @RequestBody ProductoDto actualizarProducto) {
        ProductoDto updatedProducto = productoService.actualizarProducto(productoId, actualizarProducto);
        return ResponseEntity.ok(updatedProducto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteProducto(@PathVariable("id") Long productoId) {
        Map<String, String> response = new HashMap<>();
        
        try {
            productoService.eliminarProducto(productoId);
            response.put("mensaje", "Producto eliminado correctamente");
            return ResponseEntity.ok(response);
            
        } catch (DataIntegrityViolationException e) {
            response.put("error", "No se pudo eliminar el producto porque tiene días asociados. Elimine primero los días del producto.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            
        } catch (Exception e) {
            response.put("error", "Error interno del servidor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> subirImagen(@RequestParam("imagen") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (file.isEmpty()) {
                response.put("error", "No se seleccionó ningún archivo");
                return ResponseEntity.badRequest().body(response);
            }
            
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                response.put("error", "El archivo debe ser una imagen");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (file.getSize() > 5 * 1024 * 1024) {
                response.put("error", "El archivo no puede ser mayor a 5MB");
                return ResponseEntity.badRequest().body(response);
            }
            
            String uploadDir = baseUploadDir.replace("/categorias", "/productos");
            File directory = new File(uploadDir);
            
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new RuntimeException("No se pudo crear el directorio: " + directory.getAbsolutePath());
                }
            }
            
            String originalName = file.getOriginalFilename();
            if (originalName == null) {
                originalName = "producto";
            }
            
            String extension = "";
            if (originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }
            String uniqueName = UUID.randomUUID().toString() + extension;
            
            File destFile = new File(directory, uniqueName);
            file.transferTo(destFile);
            
            if (!destFile.exists()) {
                throw new RuntimeException("El archivo no se guardó correctamente");
            }
            
            // ✅ CAMBIO CRÍTICO: Devolver URL relativa (el frontend construirá la URL completa)
            String fileUrl = "/uploads/productos/" + uniqueName;
            
            response.put("rutaImagen", fileUrl);
            response.put("url", fileUrl);
            response.put("mensaje", "Imagen subida exitosamente");
            response.put("nombreArchivo", uniqueName);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Error al subir archivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}