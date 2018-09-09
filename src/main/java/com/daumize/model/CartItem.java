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

	public CartItem(int productId, int quantity, BigDecimal price) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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
