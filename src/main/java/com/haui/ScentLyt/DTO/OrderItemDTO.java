package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  OrderItemDTO {

    @JsonProperty("order_id")
    @NotNull(message = "{order_item.order_id.not_null}")
    private Integer orderId;

    @JsonProperty("product_id")
    @NotNull(message = "{order_item.product_id.not_null}")
    private Integer productId;

    @JsonProperty("quantity")
    @NotNull(message = "{order_item.quantity.not_null}")
    @Min(value = 1, message = "{order_item.quantity.min}")
    private Integer quantity;

    @JsonProperty("price")
    @NotNull(message = "{order_item.price.not_null}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{order_item.price.positive}")
    private BigDecimal price;
}
