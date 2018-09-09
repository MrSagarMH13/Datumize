package com.daumize.http;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.daumize.db.DataProvider;
import com.daumize.handlers.RemoveItemHandler;
import com.daumize.handlers.GetCartHandler;
import com.daumize.handlers.AddItemHandler;
import com.daumize.handlers.ProductsGetHandler;
import com.daumize.handlers.RootHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * 
 * @author mrsagar
 *
 */
public class DatumizeHttpServer {

	public static void main(String[] args) {
		int port = 9090;
		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			System.out.println("server started at " + port);
			server.createContext("/", new RootHandler());
			server.createContext("/products", new ProductsGetHandler());
			server.createContext("/cart", new AddItemHandler());
			server.createContext("/cart/get", new GetCartHandler());
			server.createContext("/cart/remove", new RemoveItemHandler());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static {
		DataProvider dataProvider = new DataProvider();
		dataProvider.loadData();
	}

}
