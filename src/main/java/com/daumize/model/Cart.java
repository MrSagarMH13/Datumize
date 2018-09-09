package com.daumize.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author mrsagar
 *
 */
public class Cart {

	private int cartId;
	private int userId;
	private BigDecimal totalValue;
	private List<CartItem> items;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

}
