package com.haui.ScentLyt.service.impl;

import com.haui.ScentLyt.DTO.OrderDTO;
import com.haui.ScentLyt.DTO.UpdateStatusOrderDTO;
import com.haui.ScentLyt.entity.Order;
import com.haui.ScentLyt.entity.User;
import com.haui.ScentLyt.repository.OrderRepository;
import com.haui.ScentLyt.repository.UserRepository;
import com.haui.ScentLyt.response.order.OrderDetailResponse;
import com.haui.ScentLyt.response.order.OrderResponse;
import com.haui.ScentLyt.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    final List<String> paymentMethods = List.of("cod", "other");
    final List<String> statuses = List.of("pending", "processing", "shipped", "delivered");

    @Override
    public Page<OrderResponse> getOrders(String paymentMethod, String paymentStatus, String shippingAddress,
                                         String orderStatus, LocalDate createdAt, LocalDate updatedAt,
                                         Pageable pageable) {
        Page<Order> orders = orderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (paymentMethod != null) {
                predicates.add(cb.like(cb.lower(root.get("paymentMethod")), "%" + paymentMethod.toLowerCase() + "%"));
            }
            if (paymentStatus != null) {
                predicates.add(cb.like(cb.lower(root.get("paymentStatus")), "%" + paymentStatus.toLowerCase() + "%"));
            }
            if (shippingAddress != null) {
                predicates.add(cb.like(cb.lower(root.get("shippingAddress")), "%" + shippingAddress.toLowerCase() + "%"));
            }
            if (orderStatus != null) {
                predicates.add(cb.like(cb.lower(root.get("orderStatus")), "%" + orderStatus.toLowerCase() + "%"));
            }
            if (createdAt != null) {
                predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("createdAt")),
                        java.sql.Date.valueOf(createdAt)));
            }
            if (updatedAt != null) {
                predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("updatedAt")),
                        java.sql.Date.valueOf(updatedAt)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return orders.map(OrderResponse::fromOrder);
    }

    @Override
    public Page<OrderResponse> getOrders(Integer userId, String paymentMethod, String paymentStatus, String shippingAddress,
                                         String orderStatus, LocalDate createdAt, LocalDate updatedAt,
                                         Pageable pageable) {
        Page<Order> orders = orderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), userId));
            }
            if (paymentMethod != null) {
                predicates.add(cb.like(cb.lower(root.get("paymentMethod")), "%" + paymentMethod.toLowerCase() + "%"));
            }
            if (paymentStatus != null) {
                predicates.add(cb.like(cb.lower(root.get("paymentStatus")), "%" + paymentStatus.toLowerCase() + "%"));
            }
            if (shippingAddress != null) {
                predicates.add(cb.like(cb.lower(root.get("shippingAddress")), "%" + shippingAddress.toLowerCase() + "%"));
            }
            if (orderStatus != null) {
                predicates.add(cb.like(cb.lower(root.get("orderStatus")), "%" + orderStatus.toLowerCase() + "%"));
            }
            if (createdAt != null) {
                predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("createdAt")),
                        java.sql.Date.valueOf(createdAt)));
            }
            if (updatedAt != null) {
                predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("updatedAt")),
                        java.sql.Date.valueOf(updatedAt)));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return orders.map(OrderResponse::fromOrder);
    }

    @Override
    public Order updateStatus(Integer id, UpdateStatusOrderDTO orderDTO) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setOrderStatus(orderDTO.getStatus());
            orderRepository.save(order);
            return order;
        }
        return null;
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order getOrderById(Integer id, User user) {
        return orderRepository.findByIdAndUser(id, user).orElse(null);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails(Integer orderId) {
        // This part can be extended based on your `OrderDetail` entity.
        // For now, assuming you need to retrieve order details like variants.
        List<OrderDetailResponse> orderDetails = new ArrayList<>();
        // Assuming you have a method to get order details.
        // Placeholder for actual retrieval logic.
        return orderDetails;
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setCreatedAt(LocalDate.now());
        order.setUpdatedAt(LocalDate.now());

        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        user.ifPresent(order::setUser);

        return orderRepository.save(order);
    }

    @Override
    public List<String> validOrder(OrderDTO orderDTO, BindingResult bindingResult) {
        List<String> errors = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errors.add(fieldError.getDefaultMessage());
            }
        }

        // Validate payment method
        if (!paymentMethods.contains(orderDTO.getPaymentMethod())) {
            errors.add("Payment method is not supported.");
        }

        // Validate order status
        if (!statuses.contains(orderDTO.getStatus())) {
            errors.add("Order status is not supported.");
        }

        return errors;
    }
}
