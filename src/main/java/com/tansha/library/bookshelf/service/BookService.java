package com.tansha.library.bookshelf.service;

import java.util.List;

import com.tansha.library.bookshelf.model.Book;
import com.tansha.library.bookshelf.model.SearchResult;

public interface BookService {
	List<Book> getAllBooks();
	Book getBookById(int bookId);
    boolean addBook(Book book);
    void updateBook(Book book);
    void deleteBook(int bookId);
    List<Object[]> searchBooks(String searchStr);
    List<SearchResult> selectSearchBooks(int readingLevelId, int categoryId,int languageId,int publisherId);
    List<Object[]> bookDetails(int bookId);
    
    List<Object[]> getFeaturedBooks();
   
    
}
