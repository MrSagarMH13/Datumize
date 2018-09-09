package com.daumize.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

	int HTTP_STATUS = 404;

	@SuppressWarnings("resource")
	public void handle(HttpExchange he) throws IOException {
		OutputStream os = null;
		String data = "No data found";
		try {
			Map<String, String> params = null;
			if (he.getRequestURI().getRawQuery() != null && he.getRequestURI().getRawQuery() != "")
				params = QueryParser.parseQueryString(he.getRequestURI().getRawQuery());
			List<Product> products = getProducts(params);
			if (products != null && !products.isEmpty()) {
				data = JsonParser.toString(products);
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

	/**
	 * This method is used to fetch products on the basis of request.
	 * 
	 * @param params
	 * @return
	 */
	private List<Product> getProducts(Map<String, String> params) {
		if (params != null) {
			if (params.containsKey("deptId") && params.containsKey("catId")) {
				List<Integer> productIds = getProductIdsByDepartmentAndCategoryId(
						Integer.parseInt(params.get("deptId")), Integer.parseInt(params.get("catId")));
				return getProductsByProductIds(productIds);
			} else if (params.containsKey("deptId")) {
				List<Integer> productIds = getProductIdsByDepartmentId(Integer.parseInt(params.get("deptId")));
				return getProductsByProductIds(productIds);
			} else if (params.containsKey("catId")) {
				List<Integer> productIds = getProductIdsByCategoryId(Integer.parseInt(params.get("catId")));
				return getProductsByProductIds(productIds);
			} else if (params.containsKey("name")) {
				return getProductIdsByName(params.get("name").toLowerCase());
			}
		}
		return new ArrayList<>();
	}

	/**
	 * This method filters products based on category id and department id
	 * 
	 * @param deptId
	 * @param catId
	 * @return
	 */
	private List<Integer> getProductIdsByDepartmentAndCategoryId(Integer deptId, Integer catId) {
		List<Integer> productIds = DataProvider.productAssociations.stream()
				.filter(x -> x.getDepartmentId() == deptId && x.getCategoryId() == catId).map(x -> x.getProductId())
				.collect(Collectors.toList());
		return productIds;
	}

	/**
	 * This method filters products by name
	 * 
	 * @param prodName
	 * @return
	 */
	private List<Product> getProductIdsByName(String prodName) {
		List<Product> filteredProducts = DataProvider.products.stream()
				.filter(u -> u.getProductName().toLowerCase().contains(prodName)
						|| u.getProductName().toLowerCase().startsWith(prodName)
						|| u.getProductName().toLowerCase().endsWith(prodName))
				.collect(Collectors.toList());
		return filteredProducts;
	}

	/**
	 * This method filters products by category id
	 * 
	 * @param catId
	 * @return
	 */
	private List<Integer> getProductIdsByCategoryId(Integer catId) {
		List<Integer> productIds = DataProvider.productAssociations.stream().filter(x -> x.getCategoryId() == catId)
				.map(x -> x.getProductId()).collect(Collectors.toList());
		return productIds;

	}

	/**
	 * This method is used to filter products by department id
	 * 
	 * @param deptId
	 * @return
	 */

	private List<Integer> getProductIdsByDepartmentId(Integer deptId) {
		List<Integer> productIds = DataProvider.productAssociations.stream().filter(x -> x.getDepartmentId() == deptId)
				.map(x -> x.getProductId()).collect(Collectors.toList());
		return productIds;

	}

	/**
	 * This method get product data by product ids
	 * 
	 * @param productIds
	 * @return
	 */
	private List<Product> getProductsByProductIds(List<Integer> productIds) {
		return DataProvider.products.stream()
				.filter(product -> productIds.stream().filter(prodId -> product.getProductId() == prodId)
						.anyMatch(prodId -> prodId == product.getProductId()))
				.collect(Collectors.toList());

	}

}
