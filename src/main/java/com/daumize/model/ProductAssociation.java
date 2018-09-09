package com.daumize.model;

/**
 * 
 * @author mrsagar
 * 
 *         This class is used to manage the association between product,
 *         category and department to achive "Some products can be at the same
 *         time in different departments and categories."
 */
public class ProductAssociation {

	private Integer id;
	private Integer productId;
	private Integer categoryId;
	private Integer departmentId;

	public ProductAssociation(int productId, int categoryId, int departmentId) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.departmentId = departmentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

}
