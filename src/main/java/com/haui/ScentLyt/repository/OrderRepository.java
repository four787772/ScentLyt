package com.haui.ScentLyt.repository;

import com.haui.ScentLyt.entity.Order;
import com.haui.ScentLyt.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE " +
            "(:payment_method IS NULL OR :payment_method = '' OR o.paymentMethod LIKE %:payment_method%) " +
            "AND (:payment_status IS NULL OR :payment_status = '' OR o.paymentStatus LIKE %:payment_status%) " +
            "AND (:order_status IS NULL OR :order_status = '' OR o.orderStatus LIKE %:order_status%) " +
            "AND (:shipping_address IS NULL OR :shipping_address = '' OR o.shippingAddress LIKE %:shipping_address%) " +
            "AND (:created_at IS NULL OR o.createdAt = :created_at)")
    Page<Order> findAllOrder(@Param("payment_method") String paymentMethod,
                             @Param("payment_status") String paymentStatus,
                             @Param("order_status") String orderStatus,
                             @Param("shipping_address") String shippingAddress,
                             @Param("created_at") Instant createdAt,
                             Pageable pageable);

    @Query("SELECT o FROM Order o WHERE " +
            "(:user_id IS NULL OR o.user.id = :user_id) " +
            "AND (:payment_method IS NULL OR :payment_method = '' OR o.paymentMethod LIKE %:payment_method%) " +
            "AND (:payment_status IS NULL OR :payment_status = '' OR o.paymentStatus LIKE %:payment_status%) " +
            "AND (:order_status IS NULL OR :order_status = '' OR o.orderStatus LIKE %:order_status%) " +
            "AND (:shipping_address IS NULL OR :shipping_address = '' OR o.shippingAddress LIKE %:shipping_address%) " +
            "AND (:created_at IS NULL OR o.createdAt = :created_at)")
    Page<Order> findAllOrder(@Param("user_id") Integer userId,
                             @Param("payment_method") String paymentMethod,
                             @Param("payment_status") String paymentStatus,
                             @Param("order_status") String orderStatus,
                             @Param("shipping_address") String shippingAddress,
                             @Param("created_at") Instant createdAt,
                             Pageable pageable);

    Order findByIdAndUser(Integer id, User user);

    List<Order> findByUserUserId(Integer userId);
}
