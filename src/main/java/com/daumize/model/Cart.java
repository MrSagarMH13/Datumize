package com.daumize.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author mrsagar
 *
 */
public class Cart {

	private List<CartItem> items;
	private BigDecimal cartTotal = BigDecimal.ZERO;

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public BigDecimal getCartTotal() {
		for (CartItem cartItem : items) {
			cartTotal = cartTotal.add(cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity())));
		}
		return cartTotal;
	}

	public void setCartTotal(BigDecimal cartTotal) {
		this.cartTotal = cartTotal;
	}

	public void addItem(CartItem cartItem) {
		if (items == null)
			items = new ArrayList<>();
		items.add(cartItem);
	}

	public void removeItem(List<CartItem> cartItems) {
		if (items != null && !items.isEmpty()) {
			items.removeAll(cartItems);
		}

	}
}
