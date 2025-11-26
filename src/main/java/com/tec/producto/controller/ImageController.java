package com.tec.producto.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/uploads")
public class ImageController {

    @Value("${app.upload.dir:/uploads/categorias}")
    private String baseUploadDir;

    @GetMapping("/productos/{filename:.+}")
    public ResponseEntity<byte[]> getProductoImage(@PathVariable String filename) {
        return serveImage("/productos", filename);
    }

    @GetMapping("/categorias/{filename:.+}")
    public ResponseEntity<byte[]> getCategoriaImage(@PathVariable String filename) {
        return serveImage("/categorias", filename);
    }

    private ResponseEntity<byte[]> serveImage(String subfolder, String filename) {
        try {
            // Construir la ruta base: /uploads (sin categorias)
            String uploadDir = baseUploadDir.replace("/categorias", "");
            if (!uploadDir.endsWith("/")) {
                uploadDir = uploadDir + "/";
            }
            
            // Construir la ruta completa del archivo
            Path imagePath = Paths.get(uploadDir, subfolder, filename);
            
            // Crear directorios si no existen
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            
            File subfolderDir = new File(uploadDir, subfolder);
            if (!subfolderDir.exists()) {
                subfolderDir.mkdirs();
            }
            
            File imageFile = imagePath.toFile();
            
            if (!imageFile.exists() || !imageFile.isFile()) {
                // Log para debugging
                System.err.println("Image not found: " + imagePath.toString());
                System.err.println("Upload dir: " + uploadDir);
                System.err.println("Subfolder: " + subfolder);
                System.err.println("Filename: " + filename);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            
            byte[] imageBytes = Files.readAllBytes(imagePath);
            
            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = "image/jpeg"; // Default
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(imageBytes.length);
            headers.setCacheControl("public, max-age=3600");
            
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            
        } catch (IOException e) {
            System.err.println("Error serving image: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

