package com.haui.ScentLyt.response.productImage;

import com.haui.ScentLyt.entity.ProductImage;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImageResponse {
    private Integer id;
    private Integer productId;
    private String imageUrl;

    public static ProductImageResponse fromProductImage(ProductImage image) {
        ProductImageResponse response = new ProductImageResponse();
        BeanUtils.copyProperties(image, response);
        response.setId(image.getId());
        return response;
    }
}
