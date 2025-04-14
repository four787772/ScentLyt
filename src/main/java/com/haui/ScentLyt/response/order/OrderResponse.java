package com.haui.ScentLyt.response.order;

import com.haui.ScentLyt.entity.Order;
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
public class OrderResponse {
    private Integer id;
    private Integer userId;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String shippingAddress;
    private String orderStatus;
    private Date createdAt;
    private Date updatedAt;

    public static OrderResponse fromOrder(Order order) {
        OrderResponse response = new OrderResponse();
        BeanUtils.copyProperties(order, response);
        response.setId(order.getId());
        return response;
    }
}
