package com.haui.ScentLyt.response.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private Integer id;

    @JsonProperty("quantity")
    private Integer numberOfProduct;

    private Double price;

}
