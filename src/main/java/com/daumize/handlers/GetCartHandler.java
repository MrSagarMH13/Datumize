package com.daumize.handlers;

import java.io.IOException;
import java.io.OutputStream;

import com.daumize.db.DataProvider;
import com.daumize.model.Cart;
import com.daumize.util.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 
 * @author mrsagar
 *
 */
public class GetCartHandler implements HttpHandler {

	int HTTP_STATUS = 404;

	/**
	 * Used to get cart with cart items
	 */
	@SuppressWarnings("resource")
	public void handle(HttpExchange he) throws IOException {
		OutputStream os = null;
		String data = "No data found";
		try {
			Cart cart = getCart();
			if (cart != null) {
				data = JsonParser.toString(cart);
				HTTP_STATUS = 200;
			}
			he.sendResponseHeaders(HTTP_STATUS, data.length());
			os = he.getResponseBody();
			os.write(data.getBytes());
		} catch (Exception e) {
			HTTP_STATUS = 500;
			data = "Internal server error";
			System.err.println("I am error" + e.toString());
			he.sendResponseHeaders(HTTP_STATUS, 0);
			os = he.getResponseBody();
			os.write(data.getBytes());
		} finally {
			os.close();
		}

	}

	private Cart getCart() {
		return DataProvider.getCart();
	}

}
