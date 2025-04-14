package com.haui.ScentLyt.response.orderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class ListOrderItemResponse {
    private List<OrderItemResponse> orderItems;
    private int totalPages;
}
