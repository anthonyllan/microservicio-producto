package com.tec.producto.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.beans.factory.annotation.Value;

import com.tec.producto.dto.CategoriaDto;
import com.tec.producto.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {	
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Value("${app.upload.dir:/uploads/categorias}")
	private String uploadDir;
	
	@GetMapping
    public List<CategoriaDto> obtenerTodas() {
        return categoriaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> obtenerPorId(@PathVariable Long id) {
        CategoriaDto categoriaDto = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoriaDto);
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> guardarCategoria(@RequestBody CategoriaDto categoriadto) {
        CategoriaDto newCategoria = categoriaService.guardarCategoria(categoriadto);
        return ResponseEntity.status(201).body(newCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDto categoriadto) {
        CategoriaDto actualizarCategoria = categoriaService.actualizarCategoria(id, categoriadto);
        return ResponseEntity.ok(actualizarCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
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
            
            File directory = new File(uploadDir);
            
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new RuntimeException("No se pudo crear el directorio: " + directory.getAbsolutePath());
                }
            }
            
            String originalName = file.getOriginalFilename();
            if (originalName == null) {
                originalName = "imagen";
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
            
            String fileUrl = "/uploads/categorias/" + uniqueName;
            
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