package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @JsonProperty("name")
    @NotBlank(message = "{product.name.not_blank}")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    @NotNull(message = "{product.price.not_null}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{product.price.positive}")
    private BigDecimal price;

    @JsonProperty("stock_quantity")
    @Min(value = 0, message = "{product.stock.min}")
    private Integer stockQuantity = 0;

    @JsonProperty("fragrance")
    @NotBlank(message = "{product.fragrance.not_blank}")
    private String fragrance;

    @JsonProperty("color")
    @NotBlank(message = "{product.color.not_blank}")
    private String color;
}
