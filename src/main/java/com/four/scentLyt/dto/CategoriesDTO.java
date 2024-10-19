package com.four.scentLyt.dto;

import java.util.Date;
import java.util.List;

import com.four.scentLyt.entity.Product;


import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class CategoriesDTO {
	private String categoryId;
	private String name;
	private String description;
	private Date createdAt;
	private Date updateAt;
	private List<Product> products;
	
	@Data
	@NoArgsConstructor
	public static class ProductDTO{
		private String productId;
		private String name;
		private double price;
	}
}
