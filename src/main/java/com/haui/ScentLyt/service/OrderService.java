package com.haui.ScentLyt.service;

import com.haui.ScentLyt.DTO.OrderDTO;
import com.haui.ScentLyt.DTO.UpdateStatusOrderDTO;
import com.haui.ScentLyt.entity.Order;
import com.haui.ScentLyt.entity.User;
import com.haui.ScentLyt.response.order.OrderDetailResponse;
import com.haui.ScentLyt.response.order.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    Page<OrderResponse> getOrders(String paymentMethod, String paymentStatus, String shippingAddress,
                                  String orderStatus, LocalDate createdAt, LocalDate updatedAt,
                                  Pageable pageable);

    Page<OrderResponse> getOrders(Integer userId, String paymentMethod, String paymentStatus, String shippingAddress,
                                  String orderStatus, LocalDate createdAt, LocalDate updatedAt,
                                  Pageable pageable);

    Order updateStatus(Integer id, UpdateStatusOrderDTO orderDTO);

    Order getOrderById(Integer id);

    Order getOrderById(Integer id, User user);

    List<OrderDetailResponse> getOrderDetails(Integer orderId);

    Order createOrder(OrderDTO orderDTO);

    List<String> validOrder(OrderDTO orderDTO, BindingResult bindingResult);
}
