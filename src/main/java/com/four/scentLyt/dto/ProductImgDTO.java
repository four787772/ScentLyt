package com.four.scentLyt.dto;

import com.four.scentLyt.entity.Product;
import com.four.scentLyt.entity.ProductImg;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class ProductImgDTO {
	private int id;
	private Product product;
	
	@Data
	@NoArgsConstructor
	public static class Product{
		private String productId;
		private String name;
		private double price;
	}
}
