package com.tansha.library.bookshelf.service.impl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansha.library.bookshelf.model.BookCategory;
import com.tansha.library.bookshelf.repository.BookCategoryRepository;
import com.tansha.library.bookshelf.service.BookCategoryService;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {
	@Autowired
	private BookCategoryRepository bookCategoryRepository;
	@Override
	public BookCategory getBookCategoryById(int booksCategoryId) {
		BookCategory obj = bookCategoryRepository.findByCategoryId(booksCategoryId).get(0);
		return obj;
	}	
	@Override
	public List<BookCategory> getAllBookCategories(){
		
		List<BookCategory> list = new ArrayList<>();
		bookCategoryRepository.findAll().forEach(e -> list.add(e));
		return list;
	}
	@Override
	public synchronized boolean addBookCategory(BookCategory bookCategory){
			
	        List<BookCategory> list = bookCategoryRepository.findBycategoryName((bookCategory.getCategoryName())); 	
                if (list.size() > 0) {
    	           return false;
                } else {
    	        bookCategoryRepository.save(bookCategory);
    	        return true;
       }
	}
	@Override
	public void updateBookCategory(BookCategory bookCategory) {
		bookCategoryRepository.save(bookCategory);
	}
	@Override
	public void deleteBookCategory(int booksCategoryId) {
		bookCategoryRepository.delete(getBookCategoryById(booksCategoryId));
	}
	
	@Override
	public List<BookCategory> getHeaderCategories(){
		return bookCategoryRepository.getHeaderCategories(1);
	}
}
