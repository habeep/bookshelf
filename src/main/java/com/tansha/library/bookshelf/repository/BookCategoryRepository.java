package com.tansha.library.bookshelf.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.BookCategory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface BookCategoryRepository extends CrudRepository<BookCategory,String> {

	List<BookCategory> findBycategoryName(String categoryName);
	List<BookCategory> findByCategoryId(int categoryId);
	
	@Query("SELECT bookCategory  from BookCategory bookCategory where bookCategory.isHeader = ?1")
	List<BookCategory> getHeaderCategories(int isHeaderFlag);
	
	@Query("SELECT bookCategory.categoryId from BookCategory bookCategory where bookCategory.categoryName LIKE  %?1%")
	List<Integer> findByCategoryNameLike(String bookCategoryName);
	
	@Query("SELECT bookCategory from BookCategory bookCategory where bookCategory.isActive = ?1")
	List<Integer> findAllActiveCategory(int isActiveFlag);
	 
}
