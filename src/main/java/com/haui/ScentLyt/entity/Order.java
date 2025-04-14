package com.haui.ScentLyt.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @ColumnDefault("'cod'")
    @Lob
    @Column(name = "payment_method")
    private String paymentMethod;

    @ColumnDefault("'pending'")
    @Lob
    @Column(name = "payment_status")
    private String paymentStatus;

    @Size(max = 255)
    @Column(name = "shipping_address")
    private String shippingAddress;

    @ColumnDefault("'pending'")
    @Lob
    @Column(name = "order_status")
    private String orderStatus;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}