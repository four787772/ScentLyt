package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionProductDTO {

    @JsonProperty("product_id")
    @NotNull(message = "{promotion_product.product_id.not_null}")
    private Integer productId;

    @JsonProperty("promotion_id")
    @NotNull(message = "{promotion_product.promotion_id.not_null}")
    private Integer promotionId;
}
