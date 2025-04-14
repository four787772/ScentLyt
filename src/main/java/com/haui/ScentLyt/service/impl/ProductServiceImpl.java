package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.ProductDTO;
import com.haui.ScentLyt.entity.Product;
import com.haui.ScentLyt.exception.DataNotFoundException;
import com.haui.ScentLyt.repository.ProductRepository;
import com.haui.ScentLyt.response.product.ProductResponse;
import com.haui.ScentLyt.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse save(ProductDTO productDTO) {
        ProductResponse response = new ProductResponse();
        try {
            Product product = new Product();
            BeanUtils.copyProperties(productDTO, product);
            Product savedProduct = productRepository.save(product);
            BeanUtils.copyProperties(savedProduct, response);
            response.setId(savedProduct.getId());
        } catch (Exception e) {
            throw new RuntimeException("Save product failed: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ProductResponse update(Integer id, ProductDTO productDTO) {
        ProductResponse response = new ProductResponse();
        try {
            Product existingProduct = getProductById(id);
            BeanUtils.copyProperties(productDTO, existingProduct);
            Product updatedProduct = productRepository.save(existingProduct);
            BeanUtils.copyProperties(updatedProduct, response);
            response.setId(updatedProduct.getId());
        } catch (Exception e) {
            throw new RuntimeException("Update product failed: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> validateInsert(ProductDTO productDTO, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        }
        return errors;
    }

    @Override
    public List<String> validateUpdate(Integer productId, ProductDTO productDTO, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        try {
            getProductById(productId);
        } catch (DataNotFoundException e) {
            errors.add("Product not found with id = " + productId);
        }

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        }

        return errors;
    }

    @Override
    public Product getProductById(Integer productId) throws DataNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        throw new DataNotFoundException("Product not found with id = " + productId);
    }

    @Override
    public Page<ProductResponse> getProducts(String name, Integer unused, Boolean alsoUnused, Pageable pageable) {
        throw new UnsupportedOperationException("Pagination with filters is not yet implemented.");
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        return convertToResponseList(products);
    }

    @Override
    public List<ProductResponse> getProductByName(String name, Boolean unused) {
        List<Product> products = productRepository.findAll();

        if (name != null && !name.trim().isEmpty()) {
            String keyword = name.toLowerCase();
            products = products.stream()
                    .filter(p -> p.getName() != null && p.getName().toLowerCase().contains(keyword))
                    .toList();
        }

        return convertToResponseList(products);
    }

    @Override
    @Transactional
    public void delete(Integer productId) {
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            throw new RuntimeException("Delete product failed: " + e.getMessage());
        }
    }

    private List<ProductResponse> convertToResponseList(List<Product> products) {
        List<ProductResponse> responses = new ArrayList<>();
        for (Product product : products) {
            ProductResponse response = new ProductResponse();
            BeanUtils.copyProperties(product, response);
            response.setId(product.getId());
            responses.add(response);
        }
        return responses;
    }
}
