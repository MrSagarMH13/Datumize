package com.daumize.handlers;

import java.io.IOException;

import com.daumize.db.DataProvider;
import com.daumize.model.Cart;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 
 * @author mrsagar
 *
 */
public class ClearCartHandler implements HttpHandler {
	/**
	 * This is used to refresh the cart. For fresh shopping
	 */
	@Override
	public void handle(HttpExchange he) throws IOException {
		DataProvider.cart = new Cart();
	}

}
