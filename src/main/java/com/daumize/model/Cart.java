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
		return this.cartTotal;
	}

	public void setCartTotal(BigDecimal cartTotal) {
		this.cartTotal = cartTotal;
	}

	public void addItem(CartItem cartItem) {
		if (items == null)
			items = new ArrayList<>();
		items.add(cartItem);
		updateCartTotal(cartItem, true);
	}

	public void removeItem(CartItem cartItem) {
		if (items != null && !items.isEmpty()) {
			items.remove(cartItem);
			updateCartTotal(cartItem, false);

		}

	}

	public void updateCartTotal(CartItem cartItem, boolean isAdd) {
		if (isAdd)
			this.cartTotal = cartTotal.add((cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity()))));
		else
			this.cartTotal = cartTotal.subtract((cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity()))));
	}
}
