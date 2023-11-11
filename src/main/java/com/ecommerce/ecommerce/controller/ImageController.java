package com.ecommerce.ecommerce.controller;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
@RestController
@RequestMapping("/")
public class ImageController {
    private final String imageFolder = "images/";

    @GetMapping("images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        // Construir la ruta completa al archivo de imagen
        Path imagePath = Paths.get(imageFolder).resolve(imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        // Verificar si el archivo existe y es legible
        if (resource.exists() && resource.isReadable()) {
            // Configurar el encabezado de respuesta para indicar el tipo de contenido
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Puedes ajustar según el tipo de imagen

            // Devolver la imagen como una respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } else {
            // Devolver una respuesta 404 si la imagen no se encuentra
            return ResponseEntity.notFound().build();
        }
    }
}
