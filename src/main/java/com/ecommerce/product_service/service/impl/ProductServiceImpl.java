package com.ecommerce.product_service.service.impl;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.mapper.ProductMapper;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import com.ecommerce.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements ProductService {

    //inyectamos el repositorio y el mapper
    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {

        // Convertir DTO a Model
        Product product = mapper.toProduct(requestDTO);

        // Guardar en Mongo (genera ID)
        Product savedProduct = repository.save(product);

        // Convertir Model a DTO (con ID)
        return mapper.toProductResponseDTO(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(String id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequestDTO) {
        // Busca producto existente
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        // Actualizar con datos del DTO
        mapper.updateProductFromDTO(productRequestDTO, product);

        // Guardar cambios
        Product updatedProduct = repository.save(product);

        // Devolver como DTO
        return mapper.toProductResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {
        if(!repository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        repository.deleteById(id);

    }


}
