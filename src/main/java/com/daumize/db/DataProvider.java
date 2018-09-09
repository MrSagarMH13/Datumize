package com.daumize.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.daumize.model.Product;
import com.daumize.model.ProductAssociation;

/**
 * 
 * @author mrsagar
 *
 */
public class DataProvider {

	private static List<Product> products = new ArrayList<>();
	private static Map<Integer, String> categoryMap = new HashMap<>();
	private static Map<Integer, String> departmentMap = new HashMap<>();
	private static List<ProductAssociation> productAssociations = new ArrayList<>();

	public List<Product> getProducts(Map<String, String> params) {
		if (params != null) {
			if (params.containsKey("deptId") && params.containsKey("catId")) {

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
		return products.isEmpty() ? loadProducts() : products;
	}

	private List<Product> getProductIdsByName(String prodName) {
		List<Product> filteredProducts = products.stream()
				.filter(u -> u.getProductName().toLowerCase().contains(prodName)
						|| u.getProductName().toLowerCase().startsWith(prodName)
						|| u.getProductName().toLowerCase().endsWith(prodName))
				.collect(Collectors.toList());
		return filteredProducts;
	}

	private List<Integer> getProductIdsByCategoryId(Integer catId) {
		List<Integer> productIds = productAssociations.stream().filter(x -> x.getCategoryId() == catId)
				.map(x -> x.getProductId()).collect(Collectors.toList());
		return productIds;

	}

	private List<Product> getProductsByProductIds(List<Integer> productIds) {
		return products.stream()
				.filter(product -> productIds.stream().filter(prodId -> product.getProductId() == prodId)
						.anyMatch(prodId -> prodId == product.getProductId()))
				.collect(Collectors.toList());

	}

	private List<Integer> getProductIdsByDepartmentId(Integer deptId) {
		List<Integer> productIds = productAssociations.stream().filter(x -> x.getDepartmentId() == deptId)
				.map(x -> x.getProductId()).collect(Collectors.toList());
		return productIds;

	}

	public void loadData() {
		loadProducts();
		loadCategories();
		loadDepartments();
		loadProductCatDeptAssociation();
	}

	private List<ProductAssociation> loadProductCatDeptAssociation() {
		BufferedReader reader = readFile("/db/productdepartcat.txt");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				productAssociations.add(new ProductAssociation(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
						Integer.parseInt(data[2])));
			}
		} catch (IOException e) {
			System.err.println("Got error while loading product/category/department association.." + e.toString());
		}
		return productAssociations;

	}

	private Map<Integer, String> loadDepartments() {
		BufferedReader reader = readFile("/db/departments.txt");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				departmentMap.put(Integer.parseInt(data[0]), data[1]);
			}
		} catch (IOException e) {
			System.err.println("Got error while loading departments.." + e.toString());
		}
		return departmentMap;

	}

	private Map<Integer, String> loadCategories() {
		BufferedReader reader = readFile("/db/categories.txt");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				categoryMap.put(Integer.parseInt(data[0]), data[1]);
			}
		} catch (IOException e) {
			System.err.println("Got error while loading categories.." + e.toString());
		}
		return categoryMap;

	}

	/**
	 * This is used to get products based on filters
	 * 
	 * @return
	 */
	private List<Product> loadProducts() {
		BufferedReader reader = readFile("/db/products.txt");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				products.add(new Product(Integer.parseInt(data[0]), data[1], new BigDecimal(data[2])));
				System.err.println(Integer.parseInt(data[0]) + data[1] + new BigDecimal(data[2]));
			}

		} catch (IOException e) {
			System.err.println("Got error while loading produts.." + e.toString());
		}
		return null;
	}

	// Get file from resources folder
	private BufferedReader readFile(String filePath) {
		InputStream is = DataProvider.class.getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}

}
