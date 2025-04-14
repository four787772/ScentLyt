package com.haui.ScentLyt.response.orderItem;

import com.haui.ScentLyt.entity.OrderItem;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemResponse {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;

    public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        BeanUtils.copyProperties(orderItem, response);
        response.setId(orderItem.getId()); // vì tên ID trong entity là orderItemId
        return response;
    }
}
