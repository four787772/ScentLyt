package com.four.scentLyt.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "productImg")
@Data
@NoArgsConstructor

public class ProductImg {
	@Column(name = "productImg")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "img")
	private String img;
	
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	
}
