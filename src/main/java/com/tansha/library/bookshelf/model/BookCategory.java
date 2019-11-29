package com.tansha.library.bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
@Entity
@Table(name="tbl_books_category_master")
public class BookCategory {
	@Id
	@GeneratedValue
	@Column(name = "categoryId")
	private int categoryId;
	@Column(name="categoryName")
	private String categoryName;
	
	@Column(name="description")
	private String description;
	
	@ColumnDefault(value="0")
	@Column(name="isHeader")
	private int isHeader;
	
	@ColumnDefault(value="1")
	@Column(name="isActive")
	private int isActive;
	
	/**
	 * @return the isActive
	 */
	public int getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public BookCategory() {
		
	}


	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}


	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}


	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the isHeader
	 */
	public int getIsHeader() {
		return isHeader;
	}

	/**
	 * @param isHeader the isHeader to set
	 */
	public void setIsHeader(int isHeader) {
		this.isHeader = isHeader;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BooksCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
	}
	
	 
	

}
