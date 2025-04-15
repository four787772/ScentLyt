package com.haui.ScentLyt.service;

import com.haui.ScentLyt.DTO.PromotionProductDTO;
import com.haui.ScentLyt.entity.PromotionProduct;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PromotionProductService {
    List<PromotionProduct> getAll();

    PromotionProduct getById(Integer id);

    PromotionProduct save(PromotionProductDTO promotionProductDTO);

    PromotionProduct update(Integer id, PromotionProductDTO promotionProductDTO);

    void delete(Integer id);

    List<String> validateInsertion(PromotionProductDTO promotionProductDTO, BindingResult bindingResult);

    List<String> validateUpdate(Integer id, PromotionProductDTO promotionProductDTO, BindingResult bindingResult);

    List<PromotionProduct> getByPromotionId(Integer promotionId);

    List<PromotionProduct> getByProductId(Integer productId);
}
