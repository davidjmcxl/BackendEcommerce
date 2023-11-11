package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Dto.RegisterProductDto;
import com.ecommerce.ecommerce.model.entities.Product;
import com.ecommerce.ecommerce.repository.ProductRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(Pageable paginacion) {
        var products=productRepository.findAll(paginacion);
        return products;
    }

    public Product registerProduct(RegisterProductDto registerProductDto ,String img) {

        if (productRepository.existsByName(registerProductDto.name())) {

            throw new ValidationException("El nombre ya existe en otro producto ");
        }
        Product product = productRepository.save(new Product(registerProductDto,img));
        return product;
    }
}
