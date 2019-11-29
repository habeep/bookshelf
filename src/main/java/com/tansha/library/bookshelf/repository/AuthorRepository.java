package com.tansha.library.bookshelf.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.Author;
import com.tansha.library.bookshelf.model.Book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface AuthorRepository extends CrudRepository<Author,String> {

	List<Author> findByAuthorName(String author);
	
	@Query("SELECT author.authorID from Author author where author.authorName LIKE  %?1%")
	List<Integer> findByAuthorNameLike(String author);
	
	List<Author> findByAuthorID(int authorID);
	
	
	@Query("SELECT author from Author author where author.isActive = ?1")
	List<Author> findAllActivAuthor(int isActiveFlag);
	
	/*@Query("SELECT authors from Authors authors where authors.authorName LIKE  %?1%")
	List<Author> findByAuthor(String searchStr);*/
	
	 
}
