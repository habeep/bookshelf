package com.tansha.library.bookshelf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
import com.tansha.library.bookshelf.model.Rating;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
@Transactional
public interface RatingRepository extends CrudRepository<Rating,String> {

	List<Rating> findById(int id);
	Optional<Rating> findByUserId(int userid);
	Optional<Rating> findByUserIdAndBookId(int userid,int bookId);
	Optional<Rating> findByBookId(int bookid);
	
	
	@Query("SELECT rating from Rating rating where rating.userId=?1 AND rating.bookId = ?2  ")
	Optional<Rating> getUserRating(int userId,int bookId);
	
	@Query("SELECT rating from Rating rating where  rating.bookId = ?1  ")
	Optional<Rating> getBookRating(int bookId);
	
	@Query("SELECT rating from Rating rating "
			+ " INNER JOIN  Book book "
			+ " ON book.bookId = rating.bookId"
			+ " where book.isbncode = ?1 ")
	List<Rating> getBookRatings(String isbnCode);
	
	@Query("SELECT rating from Rating rating "
			+ " INNER JOIN  Book book "
			+ " ON book.bookId = rating.bookId"
			+ " where rating.userId =?1  AND book.isbncode = ?2  ")
	Optional<Rating> getUserBookRating(int userId,String isbnCode);
	

	 
}