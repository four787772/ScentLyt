package com.haui.ScentLyt.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @JsonProperty("category_name")
    @NotNull(message = "{category.name.not_null}")
    @NotBlank(message = "{category.name.not_blank}")
    private String categoryName;

    @JsonProperty("active")
    private Boolean active = true;
}

