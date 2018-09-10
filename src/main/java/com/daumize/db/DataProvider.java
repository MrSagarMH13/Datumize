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

import com.daumize.model.Cart;
import com.daumize.model.Product;
import com.daumize.model.ProductAssociation;

/**
 * 
 * @author mrsagar
 *
 */
public class DataProvider {

	public static List<Product> products = new ArrayList<>();
	public static Map<Integer, String> productNameMap = new HashMap<>();
	public static Map<Integer, String> categoryMap = new HashMap<>();
	public static Map<Integer, String> departmentMap = new HashMap<>();
	public static List<ProductAssociation> productAssociations = new ArrayList<>();
	public static Cart cart = new Cart();

	/**
	 * This is the method which is used load the data from file to in-memory
	 * database
	 */

	public void loadData() {
		loadProducts();
		loadProductNameMap();
		loadCategories();
		loadDepartments();
		loadProductCatDeptAssociation();
	}

	private void loadProductNameMap() {
		BufferedReader reader = readFile("/db/products.txt");
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				productNameMap.put(Integer.parseInt(data[0]), data[1]);
			}

		} catch (IOException e) {
			System.err.println("Got error while loading produts.." + e.toString());
		}

	}

	public static Cart getCart() {
		return cart;
	}

	public static void setCart(Cart cart) {
		DataProvider.cart = cart;
	}

	/**
	 * This will load product category, department association data to in memory
	 * database. This association is managed to achive this functionality -->
	 * "Some products can be at the same time in different departments and
	 * categories"
	 * 
	 * @return
	 */
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

	/**
	 * This will load the department data from the file
	 * 
	 * @return
	 */
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

	/**
	 * This will load the category data from the file
	 * 
	 * @return
	 */
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
			}

		} catch (IOException e) {
			System.err.println("Got error while loading produts.." + e.toString());
		}
		return null;
	}

	/**
	 * This method is used to read the file from resource folder
	 * 
	 * @param filePath
	 * @return
	 */
	private BufferedReader readFile(String filePath) {
		InputStream is = DataProvider.class.getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		return reader;
	}
}
