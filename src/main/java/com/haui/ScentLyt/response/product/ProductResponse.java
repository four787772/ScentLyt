package com.haui.ScentLyt.response.product;

import com.haui.ScentLyt.entity.Product;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String fragrance;
    private String color;
    private Date createdAt;
    private Date updatedAt;

    public static ProductResponse fromProduct(Product product) {
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        response.setId(product.getId());
        return response;
    }
}
