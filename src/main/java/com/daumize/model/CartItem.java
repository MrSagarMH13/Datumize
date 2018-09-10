package com.daumize.model;

import java.math.BigDecimal;

/**
 * 
 * @author mrsagar
 *
 */
public class CartItem {

	private int productId;
	private int quantity;
	private BigDecimal price;
	private String productName;

	public CartItem(int productId, int quantity, BigDecimal price, String productName) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.productName = productName;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
