package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    @JsonProperty("user_id")
    @NotNull(message = "{order.user_id.not_null}")
    private Integer userId;

    @JsonProperty("total_amount")
    @NotNull(message = "{order.total_amount.not_null}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{order.total_amount.positive}")
    private BigDecimal totalAmount;

    @JsonProperty("payment_method")
    @NotBlank(message = "{order.payment_method.not_blank}")
    private String paymentMethod;

    @JsonProperty("payment_status")
    @NotBlank(message = "{order.payment_status.not_blank}")
    private String paymentStatus;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("order_status")
    @NotBlank(message = "{order.order_status.not_blank}")
    private String orderStatus;
}
