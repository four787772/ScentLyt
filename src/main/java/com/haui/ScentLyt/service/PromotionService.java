package com.haui.ScentLyt.service;

import com.haui.ScentLyt.DTO.PromotionDTO;
import com.haui.ScentLyt.entity.Promotion;
import com.haui.ScentLyt.response.promotion.PromotionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;

public interface PromotionService {
    Page<PromotionResponse> getPromotions(String name, Boolean active, Pageable pageable);

    List<String> validateInsertion(PromotionDTO promotionDTO, BindingResult bindingResult);

    Promotion save(PromotionDTO promotionDTO);

    Promotion update(Integer id, PromotionDTO promotionDTO);

    Promotion getPromotion(Integer promotionId);

    List<Promotion> getPromotionByIds(List<Integer> promotionIds);

    List<String> validateUpgrade(Integer id, PromotionDTO promotionDTO, BindingResult bindingResult);

    void delete(Integer id);

    List<Promotion> getPromotions(LocalDate endDate);

    void setActive(List<Promotion> promotions);
}
