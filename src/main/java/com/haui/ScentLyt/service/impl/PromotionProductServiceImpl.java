package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.PromotionProductDTO;
import com.haui.ScentLyt.entity.Product;
import com.haui.ScentLyt.entity.Promotion;
import com.haui.ScentLyt.entity.PromotionProduct;
import com.haui.ScentLyt.repository.ProductRepository;
import com.haui.ScentLyt.repository.PromotionProductRepository;
import com.haui.ScentLyt.repository.PromotionRepository;
import com.haui.ScentLyt.service.PromotionProductService;
import com.haui.ScentLyt.utils.LocalizationUtils;
import com.haui.ScentLyt.utils.MessageKeys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PromotionProductServiceImpl implements PromotionProductService {

    private final PromotionProductRepository promotionProductRepository;
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final LocalizationUtils localizationUtils;

    @Override
    public List<PromotionProduct> getAll() {
        return promotionProductRepository.findAll();
    }

    @Override
    public PromotionProduct getById(Integer id) {
        return promotionProductRepository.findById(id).orElse(null);
    }

    @Override
    public PromotionProduct save(PromotionProductDTO dto) {
        PromotionProduct entity = new PromotionProduct();
        Product product = productRepository.findById(dto.getProductId()).orElse(null);
        Promotion promotion = promotionRepository.findById(dto.getPromotionId()).orElse(null);

        if (product != null && promotion != null) {
            entity.setProduct(product);
            entity.setPromotion(promotion);
            return promotionProductRepository.save(entity);
        }

        return null;
    }

    @Override
    public PromotionProduct update(Integer id, PromotionProductDTO dto) {
        PromotionProduct existing = promotionProductRepository.findById(id).orElse(null);
        if (existing != null) {
            Product product = productRepository.findById(dto.getProductId()).orElse(null);
            Promotion promotion = promotionRepository.findById(dto.getPromotionId()).orElse(null);

            if (product != null && promotion != null) {
                existing.setProduct(product);
                existing.setPromotion(promotion);
                return promotionProductRepository.save(existing);
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        PromotionProduct promotionProduct = promotionProductRepository.findById(id).orElse(null);
        if (promotionProduct != null) {
            promotionProductRepository.delete(promotionProduct);
        }
    }

    @Override
    public List<String> validateInsertion(PromotionProductDTO dto, BindingResult result) {
        List<String> errors = new ArrayList<>();
        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errors.add(error.getDefaultMessage());
            }
            return errors;
        }

        if (!productRepository.existsById(dto.getProductId())) {
            errors.add(localizationUtils.getLocalizedMessage(MessageKeys.PRODUCT_IS_NOT_FOUND));
        }

        if (!promotionRepository.existsById(dto.getPromotionId())) {
            errors.add(localizationUtils.getLocalizedMessage(MessageKeys.PROMOTION_IS_NOT_FOUND));
        }

        return errors;
    }

    @Override
    public List<String> validateUpdate(Integer id, PromotionProductDTO dto, BindingResult result) {
        List<String> errors = validateInsertion(dto, result);
        if (!promotionProductRepository.existsById(id)) {
            errors.add(localizationUtils.getLocalizedMessage(MessageKeys.PROMOTION_PRODUCT_IS_NOT_FOUND));
        }
        return errors;
    }

    @Override
    public List<PromotionProduct> getByPromotionId(Integer promotionId) {
        return promotionProductRepository.findByPromotionId(promotionId);
    }

    @Override
    public List<PromotionProduct> getByProductId(Integer productId) {
        return promotionProductRepository.findByProductId(productId);
    }
}
