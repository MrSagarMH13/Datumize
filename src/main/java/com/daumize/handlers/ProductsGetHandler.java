package com.daumize.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.daumize.db.DataProvider;
import com.daumize.model.Product;
import com.daumize.util.JsonParser;
import com.daumize.util.QueryParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 
 * @author mrsagar
 *
 */
public class ProductsGetHandler implements HttpHandler {
	/**
	 * This class is used to filter out products on the basis of queries
	 */
	public void handle(HttpExchange he) throws IOException {
		System.out.println("I am in");
		try {
			Map<String, String> params = null;
			if (he.getRequestURI().getRawQuery() != null && he.getRequestURI().getRawQuery() != "")
				params = QueryParser.parseQueryString(he.getRequestURI().getRawQuery());
			DataProvider dataProvider = new DataProvider();
			List<Product> products = dataProvider.getProducts(params);
			String data = JsonParser.toString(products);
			he.sendResponseHeaders(200, data.length());
			OutputStream os = he.getResponseBody();
			os.write(data.getBytes());
			os.close();
		} catch (Exception e) {
			System.err.println("I am error" + e.toString());
		}
	}

}
