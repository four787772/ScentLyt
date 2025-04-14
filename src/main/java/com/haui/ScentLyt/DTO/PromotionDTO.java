package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {

    @JsonProperty("name")
    @NotBlank(message = "{promotion.name.not_blank}")
    private String name;

    @JsonProperty("discount_percentage")
    @NotNull(message = "{promotion.discount.not_null}")
    @DecimalMin(value = "0.0", message = "{promotion.discount.min}")
    @DecimalMax(value = "100.0", message = "{promotion.discount.max}")
    private Double discountPercentage;

    @JsonProperty("start_date")
    @NotNull(message = "{promotion.start_date.not_null}")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull(message = "{promotion.end_date.not_null}")
    private LocalDate endDate;

    @JsonProperty("active")
    private Boolean active = true;
}
