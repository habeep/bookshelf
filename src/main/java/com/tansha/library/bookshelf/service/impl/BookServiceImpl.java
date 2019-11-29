package com.tansha.library.bookshelf.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tansha.library.bookshelf.model.Author;
import com.tansha.library.bookshelf.model.Book;
import com.tansha.library.bookshelf.model.SearchResult;
import com.tansha.library.bookshelf.repository.AuthorRepository;
import com.tansha.library.bookshelf.repository.BookCategoryRepository;
import com.tansha.library.bookshelf.repository.BookRepository;
import com.tansha.library.bookshelf.repository.BookRepositoryForJdbc;
import com.tansha.library.bookshelf.repository.LanguageRepository;
import com.tansha.library.bookshelf.repository.PublisherRepository;
import com.tansha.library.bookshelf.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookRepositoryForJdbc jdbcBookRepository;

	@Autowired
	private LanguageRepository languageRepository;
	@Autowired
	private BookCategoryRepository bookCategoryRepository;
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Override
	public Book getBookById(int bookId) {
		Book obj = bookRepository.findByBookId(bookId);
		return obj;
	}	
	@Override
	public List<Book> getAllBooks(){
		
		List<Book> list = new ArrayList<>();
		bookRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addBook(Book book){

	        Book list = bookRepository.findByBookId(book.getBookId()); 	
                if (list == null) {
    	           return false;
                } else {
    	        bookRepository.save(book);
    	        return true;
       }
	}
	@Override
	public void updateBook(Book book) {
		bookRepository.save(book);
	}
	@Override
	public void deleteBook(int bookId) {
		bookRepository.delete(getBookById(bookId));
	}
	@Override
	public List<Object[]> getFeaturedBooks(){
		return bookRepository.getFeaturedBooks(1);
	}
	
	
		
	@Override
	public List<Object[]> searchBooks(String searchStr) {
		// TODO Auto-generated method stub
		
		
		List<Object[]> list = bookRepository.searchBooks(searchStr);
		//bookRepository.findByBookTitleOrIsbncode(searchStr, searchStr).forEach(e -> list.add(e));
		//bookRepository.saveAll(entities)(searchStr);
		return list;
	}
	
	@Override
	public List<SearchResult> selectSearchBooks(int readingLevelId, int categoryId,int languageId,int publisherId) {
		
		// TODO Auto-generated method stub
		
		//book.publisherID = ?1 OR book.categoryID = ?2 OR book.languageId = ?3 OR  readingLevels.readingLevelId = ?4
		//if(readingLevelId != -1) {
			//searchStr = ""
		//}
		List<SearchResult> list = jdbcBookRepository.selectSearchBooks(publisherId,categoryId,languageId,readingLevelId);
		//bookRepository.findByBookTitleOrIsbncode(searchStr, searchStr).forEach(e -> list.add(e));
		//bookRepository.saveAll(entities)(searchStr);
		return list;
	}
	@Override
	public List<Object[]> bookDetails(int bookId) {
		// TODO Auto-generated method stub
		
		
		List<Object[]> list = bookRepository.bookDetails(bookId);
		//bookRepository.findByBookTitleOrIsbncode(searchStr, searchStr).forEach(e -> list.add(e));
		//bookRepository.saveAll(entities)(searchStr);
		return list;
	}
}
