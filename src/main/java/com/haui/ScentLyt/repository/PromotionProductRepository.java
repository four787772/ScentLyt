package com.haui.ScentLyt.repository;

import com.haui.ScentLyt.entity.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Integer> {
    List<PromotionProduct> findByProductId(Integer productId);
    List<PromotionProduct> findByPromotionId(Integer promotionId);
}
