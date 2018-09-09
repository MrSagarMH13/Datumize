package com.daumize.model;

import java.math.BigDecimal;

public class Product {

	private int productId;
	private String productName;
	private BigDecimal price;
	private int catId;
	private int deptId;

	public Product(int productId, String productName, BigDecimal price) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
