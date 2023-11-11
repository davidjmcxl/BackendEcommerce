package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Dto.RegisterProductDto;
import com.ecommerce.ecommerce.model.entities.Product;
import com.ecommerce.ecommerce.service.ProductService;
import com.ecommerce.ecommerce.service.UploadFileService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UploadFileService upload;
    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestParam("name")
                                                       String name,
                                                   @RequestParam("description")
                                                       String description,
                                                   @RequestParam("price")
                                                       BigDecimal price,

                                                       int stock,
                                                   @RequestParam("date")
                                                       String date, @RequestParam("img") MultipartFile file)throws IOException {// cuando se crea un producto
            String nameImagen= upload.saveImage(file);

            var registerProductDto= new RegisterProductDto(name,description,price,stock,date);
        Product product = this.productService.registerProduct(registerProductDto ,nameImagen);
        return ResponseEntity.ok().body(product);

    }

}
