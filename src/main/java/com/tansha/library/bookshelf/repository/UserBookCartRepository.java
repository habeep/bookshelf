package com.tansha.library.bookshelf.repository;

import org.springframework.transaction.annotation.Transactional;
import com.tansha.library.bookshelf.model.UserBookCart;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigure
@Transactional
public interface UserBookCartRepository extends CrudRepository<UserBookCart,String> {
	

	@Query("Select ubc From UserBookCart ubc WHERE ubc.user_id = ?1 AND ubc.type_return = 0")
	List<UserBookCart> getUserCartIssue(int userid);
	
	@Query("Select ubc From UserBookCart ubc WHERE ubc.user_id = ?1 AND ubc.type_return > 0")
	List<UserBookCart>  getUserCartReturn(int userid);
	
	@Query("Select ubc From UserBookCart ubc WHERE ubc.user_id = ?1 AND ubc.book_id = ?2 AND ubc.type_return = ?3")
	UserBookCart getUserBooksInCartIssueById(int userId,int bookId,int returnFlag);
	
	@Query("Select ubc From UserBookCart ubc WHERE ubc.user_id = ?1 AND ubc.book_id = ?2 AND ubc.type_return > 0")
	List<UserBookCart> getUserBooksInCartReturnById(int userId,int bookId);

	@Query("Select ubc From UserBookCart ubc WHERE ubc.user_id = ?1 AND ubc.book_id = ?2 ")
	List<Object[]> getUserIdAndBookId(int userId,int bookId);
	
	@Query("Select ubc From UserBookCart ubc WHERE ubc.user_id = ?1 AND ubc.type_return = 0")
	List<Object[]> getUserBookCartSize(int userId);
	
	
}
