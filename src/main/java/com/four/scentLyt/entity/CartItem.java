package com.four.scentLyt.entity;


import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cartItem")
@Data
@NoArgsConstructor

public class CartItem {
	@Column(name = "cart_item_id")
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String cartItemId;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@ManyToOne()
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@ManyToOne()
	@JoinColumn(name = "product_id")
	private Product product;
	
	
	
}
