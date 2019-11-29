package com.tansha.library.bookshelf.model;

public class SearchResult {

	private int bookId;
	private String bookTitle;
	private String publisherName;
	private String authorName;
	private String categoryName;
	private String isbncode;
	private String readingLevel;
	private int readingLevelId;
	private String language;
	private int noofPages;
	private String binding;
	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	/**
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}
	/**
	 * @param bookTitle the bookTitle to set
	 */
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}
	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}
	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
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
	 * @return the isbncode
	 */
	public String getIsbncode() {
		return isbncode;
	}
	/**
	 * @param isbncode the isbncode to set
	 */
	public void setIsbncode(String isbncode) {
		this.isbncode = isbncode;
	}
	/**
	 * @return the readingLevel
	 */
	public String getReadingLevel() {
		return readingLevel;
	}
	/**
	 * @param readingLevel the readingLevel to set
	 */
	public void setReadingLevel(String readingLevel) {
		this.readingLevel = readingLevel;
	}
	/**
	 * @return the readingLevelId
	 */
	public int getReadingLevelId() {
		return readingLevelId;
	}
	/**
	 * @param readingLevelId the readingLevelId to set
	 */
	public void setReadingLevelId(int readingLevelId) {
		this.readingLevelId = readingLevelId;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * @return the noofPages
	 */
	public int getNoofPages() {
		return noofPages;
	}
	/**
	 * @param noofPages the noofPages to set
	 */
	public void setNoofPages(int noofPages) {
		this.noofPages = noofPages;
	}
	/**
	 * @return the binding
	 */
	public String getBinding() {
		return binding;
	}
	/**
	 * @param binding the binding to set
	 */
	public void setBinding(String binding) {
		this.binding = binding;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SearchResult [bookId=" + bookId + ", bookTitle=" + bookTitle + ", publisherName=" + publisherName
				+ ", authorName=" + authorName + ", categoryName=" + categoryName + ", isbncode=" + isbncode
				+ ", readingLevel=" + readingLevel + ", readingLevelId=" + readingLevelId + ", language=" + language
				+ ", noofPages=" + noofPages + ", binding=" + binding + "]";
	}
	
	
}
