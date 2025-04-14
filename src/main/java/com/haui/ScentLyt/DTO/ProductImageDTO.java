package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {

    @JsonProperty("product_id")
    @NotNull(message = "{product_image.product_id.not_null}")
    private Integer productId;

    @JsonProperty("image_url")
    @NotBlank(message = "{product_image.image_url.not_blank}")
    private String imageUrl;
}
