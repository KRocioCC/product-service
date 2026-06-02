package com.ecommerce.product_service.mapper;

import com.ecommerce.product_service.dto.ProductRequestDTO;
import com.ecommerce.product_service.dto.ProductResponseDTO;
import com.ecommerce.product_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    //no le asignamos el id porque se genera automticamente
    // DTO a Model (para guardar)
    @Mapping(target = "id", ignore = true)
    Product toProduct(ProductRequestDTO requestDTO);

    //Transformamo el producto de la base de datos a un DTO para enviarlo al cliente
    //Model a DTO (para enviar al cliente)
    ProductResponseDTO toProductResponseDTO(Product product);

    //Para actualizar
    @Mapping(target = "id", ignore = true)
    void updateProductFromDTO(ProductRequestDTO requestDTO, @MappingTarget Product product);
}