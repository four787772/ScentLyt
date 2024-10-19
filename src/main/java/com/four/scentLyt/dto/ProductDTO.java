package com.four.scentLyt.dto;

import java.util.Date;
import java.util.List;

import com.four.scentLyt.entity.Categories;
import com.four.scentLyt.entity.Product;
import com.four.scentLyt.entity.ProductImg;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
	private String productId;
	private String name;
	private String scent;
	private String ingredients;
	private String size;
	private float weight;
	private String burnTime;
	private String containerMaterial;
	private String color;
	private int price;
	private int discount;
	private int stockQuantity;
	private int salesCount;
	private Date createdAt;
	private Date updateAt;
	private Categories category;
	private List<ProductImg> productImgs;
	
	@Data
	@NoArgsConstructor
	public static class ProductImg{
		private String img;
	}
	
	@Data
	@NoArgsConstructor
	public static class Categories{
		private String name;
	}
	

}
