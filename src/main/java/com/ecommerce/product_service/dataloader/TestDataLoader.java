package com.ecommerce.product_service.dataloader;

import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
// se encargará de cargar datos de prueba en la base de datos
@RequiredArgsConstructor
public class TestDataLoader implements CommandLineRunner {

    // Aca inyectamos tus repositorios para cargar datos de prueba
    private final ProductRepository productRepository;
    @Override
    public void run(String... args) throws Exception {

        Product product = Product.builder()
                .name("Samsung Galaxy S21")
                .description("Smartphone with IA")
                .price(new BigDecimal("1200"))
                .build();
        productRepository.save(product);
        System.out.println("Product saved: " + product);
    }
}
