package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Dto.RegisterProductDto;
import com.ecommerce.ecommerce.model.Mensaje;
import com.ecommerce.ecommerce.model.entities.Product;
import com.ecommerce.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UploadFileService upload;
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
    public Product updateProduct(RegisterProductDto registerProductDto,String img ,Long id) {

        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {

            Product existingProduct = existingProductOptional.get();
            // Elimina la imagen antigua
            upload.deleteImage(existingProduct.getImagen());

            existingProduct.setName(registerProductDto.name());
            existingProduct.setDescription(registerProductDto.description());
            existingProduct.setPrice(registerProductDto.price());
            existingProduct.setStock(registerProductDto.stock());
            existingProduct.setDate(registerProductDto.date());
            existingProduct.setImagen(img);

            // Guarda y retorna el producto actualizado
            Product updateProduct = productRepository.save(existingProduct);

            return updateProduct;
        } else {
            throw new EntityNotFoundException("No se encontró un producto con el ID proporcionado");
        }

    }
    public Mensaje deleteProduct(Long id) {
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró un producto con el ID proporcionado"));

            // Elimina la imagen del producto
            upload.deleteImage(existingProduct.getImagen());

            // Elimina el producto de la base de datos
            productRepository.deleteById(id);

            return new Mensaje("Producto eliminado correctamente");

        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante el proceso de eliminación
            return new Mensaje("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
