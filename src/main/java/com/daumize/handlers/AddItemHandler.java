package com.daumize.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.daumize.db.DataProvider;
import com.daumize.model.Cart;
import com.daumize.model.CartItem;
import com.daumize.util.JsonParser;
import com.daumize.util.QueryParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 
 * @author mrsagar
 *
 */
public class AddItemHandler implements HttpHandler {

	int HTTP_STATUS = 404;

	@SuppressWarnings("resource")
	public void handle(HttpExchange he) throws IOException {
		OutputStream os = null;
		String data = "No data found";
		try {
			Map<String, String> parms = new HashMap<>();
			InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String query = br.readLine();
			parms = QueryParser.parseQueryString(query);
			Cart cart = addItemToCart(parms);
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

	private Cart addItemToCart(Map<String, String> parms) {
		Cart cart = DataProvider.getCart();
		CartItem cartItem = new CartItem(Integer.parseInt(parms.get("productId")),
				Integer.parseInt(parms.get("quantity")), new BigDecimal(parms.get("price")));
		cart.addItem(cartItem);
		return cart;
	}

}
