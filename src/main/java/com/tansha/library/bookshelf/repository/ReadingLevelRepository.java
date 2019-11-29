package com.tansha.library.bookshelf.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import com.tansha.library.bookshelf.model.ReadingLevel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface ReadingLevelRepository extends CrudRepository<ReadingLevel,String> {

	List<ReadingLevel> findByReadingLevel(String readingLevel);
	ReadingLevel findByReadingLevelId(int readingLevelId);
	
	@Query("SELECT readingLevel from ReadingLevel readingLevel where isActive = ?1")
	List<ReadingLevel> findAllActiveReadingLevel(int isActiveFlag);
	/*@Query("SELECT bookCategory.categoryId from BookCategory bookCategory where bookCategory.categoryName LIKE  %?1%")
	List<Integer> findByCategoryNameLike(String bookCategoryName);*/
	 
}
