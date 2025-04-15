package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.PromotionDTO;
import com.haui.ScentLyt.entity.Promotion;
import com.haui.ScentLyt.repository.PromotionRepository;
import com.haui.ScentLyt.response.promotion.*;
import com.haui.ScentLyt.service.PromotionService;
import com.haui.ScentLyt.utils.LocalizationUtils;
import com.haui.ScentLyt.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;
    private final LocalizationUtils localizationUtils;

    @Override
    public Page<PromotionResponse> getPromotions(String name, Boolean active, Pageable pageable) {
        Page<Promotion> promotions = promotionRepository.findAllPromotions(name, active, pageable);
        return promotions.map(PromotionResponse::fromPromotion);
    }

    @Override
    public List<String> validateInsertion(PromotionDTO promotionDTO, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }
            return errors;
        }

        if (promotionDTO.getStartDate().isEqual(promotionDTO.getEndDate())) {
            errors.add(localizationUtils.getLocalizedMessage(MessageKeys.START_DATE_AFTER_END_DATE));
        }

        return errors;
    }

    @Override
    public Promotion save(PromotionDTO promotionDTO) {
        Promotion promotion = new Promotion();
        BeanUtils.copyProperties(promotionDTO, promotion);
        promotion.setActive(true);
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion update(Integer id, PromotionDTO promotionDTO) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion != null) {
            BeanUtils.copyProperties(promotionDTO, promotion);
            return promotionRepository.save(promotion);
        }
        return null;
    }

    @Override
    public Promotion getPromotion(Integer promotionId) {
        return promotionRepository.findById(promotionId).orElse(null);
    }

    @Override
    public List<Promotion> getPromotionByIds(List<Integer> promotionIds) {
        return promotionRepository.getPromotionsByIds(promotionIds);
    }

    @Override
    public List<String> validateUpgrade(Integer id, PromotionDTO promotionDTO, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.add(fieldError.getDefaultMessage());
            }
            return errors;
        }

        Promotion promotion = promotionRepository.findById(id).orElse(null);
        if (promotion == null) {
            errors.add(localizationUtils.getLocalizedMessage(MessageKeys.PROMOTION_IS_NOT_FOUND));
        }

        if (promotionDTO.getStartDate().isEqual(promotionDTO.getEndDate())) {
            errors.add(localizationUtils.getLocalizedMessage(MessageKeys.START_DATE_AFTER_END_DATE));
        }

        return errors;
    }

    @Override
    public void delete(Integer id) {
        Promotion promotion = promotionRepository.findByIdAndActive(id, true);
        if (promotion != null) {
            promotion.setActive(false);
            promotionRepository.save(promotion);
        }
    }

    @Override
    public List<Promotion> getPromotions(LocalDate endDate) {
        return promotionRepository.getPromotionByEndDate(endDate, true);
    }

    @Override
    public void setActive(List<Promotion> promotions) {
        for (Promotion promotion : promotions) {
            promotion.setActive(false);
        }
        promotionRepository.saveAll(promotions);
    }
}
