package com.four.scentLyt.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor


public class Product {
    @Column(name = "product_id")
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String productId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "scent", nullable = false)
    private String scent;

    @Column(name = "ingrdients", nullable = false)
    private String ingredients;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "weight", nullable = false)
    private float weight;

    @Column(name = "burnTime")
    private String burnTime;

    @Column(name = "cointainer_material")
    private String containerMaterial;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "discount")
    private int discount;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;
    
    @Column(name = "sales_count")
    private int salesCount;

    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "update_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updateAt;
    

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;
    
    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductImg> productImgs;
    
    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CartItem> cartItems;
    
    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<OrderItem> orderItems;

}
