package com.haui.ScentLyt.response.promotionProduct;

import lombok.*;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PromotionProductResponse {
    private Integer id;
    private Integer productId;
    private Integer promotionId;

    public static PromotionProductResponse fromPromotionProduct(PromotionProduct pp) {
        PromotionProductResponse response = new PromotionProductResponse();
        BeanUtils.copyProperties(pp, response);
        response.setId(pp.getPromotionProductId());
        return response;
    }
}
