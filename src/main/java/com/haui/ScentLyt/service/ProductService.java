package com.haui.ScentLyt.service;

import com.haui.ScentLyt.DTO.ProductDTO;
import com.haui.ScentLyt.entity.Product;
import com.haui.ScentLyt.exception.DataNotFoundException;
import com.haui.ScentLyt.response.product.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ProductService {
    ProductResponse save(ProductDTO productDTO);

    ProductResponse update(Integer id, ProductDTO productDTO);

    List<String> validateInsert(ProductDTO productDTO, BindingResult bindingResult);

    List<String> validateUpdate(Integer productId, ProductDTO productDTO, BindingResult bindingResult);

    Product getProductById(Integer productId) throws DataNotFoundException, DataNotFoundException;

    Page<ProductResponse> getProducts(String name, String fragrance, String color, Pageable pageable);

    List<ProductResponse> getProducts();

    List<ProductResponse> getProductByName(String name, Boolean status);

    @Transactional
    void delete(Integer productId);
}
