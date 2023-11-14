package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Dto.RegisterProductDto;
import com.ecommerce.ecommerce.model.Mensaje;
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

    @GetMapping
    public ResponseEntity<Page<Product>> getProduct(@PageableDefault(size = 15) Pageable paginacion) {
        Page<Product> products = this.productService.getProducts(paginacion);
        return ResponseEntity.ok().body(products);
    }

    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestParam("name")
                                                   String name,
                                                   @RequestParam("description")
                                                   String description,
                                                   @RequestParam("price")
                                                   BigDecimal price,
                                                   int stock,
                                                   @RequestParam("date")
                                                   String date, @RequestParam("img") MultipartFile file) throws IOException {// cuando se crea un producto

        String nameImagen = upload.saveImage(file);

        var registerProductDto = new RegisterProductDto(name, description, price, stock, date);
        Product product = this.productService.registerProduct(registerProductDto, nameImagen);
        return ResponseEntity.ok().body(product);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> updateTopic(@RequestParam("name")
                                               String name,
                                               @RequestParam("description")
                                               String description,
                                               @RequestParam("price")
                                               BigDecimal price,
                                               int stock,
                                               @RequestParam("date")
                                               String date, @RequestParam("img") MultipartFile file, @PathVariable Long id) throws IOException
    {

        String nameImagen = upload.saveImage(file);

        var updateProductDto = new RegisterProductDto(name, description, price, stock, date);

        Product productUpdate = this.productService.updateProduct(updateProductDto, nameImagen, id);
        return ResponseEntity.ok().body(productUpdate);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> deleteProduct(@PathVariable Long id) {

       Mensaje respMensaje =  this.productService.deleteProduct(id);

        return ResponseEntity.ok().body(respMensaje);

    }

}
