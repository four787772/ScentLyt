package com.four.scentLyt.entity;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "oderItem")
@Data
@NoArgsConstructor

public class OrderItem {
	@Column(name = "order_item_id")
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String orderItemId;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "total__price", nullable = false)
	private double totalPrice;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
