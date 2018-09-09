package com.daumize.handlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * 
 * @author mrsagar
 *
 */
public class RootHandler implements HttpHandler {

	/**
	 * This class is used to check health check of server
	 */
	public void handle(HttpExchange he) throws IOException {
		String response = "If you can see this, it means server is up and running";
		he.sendResponseHeaders(200, response.length());
		OutputStream os = he.getResponseBody();
		os.write(response.getBytes());
		os.close();

	}

}
