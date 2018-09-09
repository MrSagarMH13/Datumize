package com.daumize.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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

	private List<Product> products;
	private Map<Integer, String> categoryMap;
	private Map<Integer, String> departmentMap;
	private List<ProductAssociation> productAssociations;

	public List<Product> getProducts(Map<String, String> params) {
		if (params != null) {
			if (params.containsKey("deptId") && params.containsKey("catId")) {

			} else if (params.containsKey("deptId")) {
				List<Integer> getProductIds = getProductIdsByDepartmentId(Integer.parseInt(params.get("deptId")));
			} else if (params.containsKey("catId")) {

			}
		}
		return products.isEmpty() ? loadProducts() : products;
	}

	private List<Integer> getProductIdsByDepartmentId(Integer deptId) {
		List<Integer> productIds = productAssociations.stream().filter(x -> x.getProductId() == deptId)
				.map(x -> x.getProductId()).collect(Collectors.toList());
		System.out.println(productIds);
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
		BufferedReader reader = readFile("/products.txt");
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
