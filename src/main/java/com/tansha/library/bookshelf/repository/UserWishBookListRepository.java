package com.tansha.library.bookshelf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tansha.library.bookshelf.model.Rating;
import com.tansha.library.bookshelf.model.UserWishBookList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface UserWishBookListRepository extends CrudRepository<UserWishBookList,String> {

	UserWishBookList findById(int id);
	UserWishBookList findByUserid(int userId);
	UserWishBookList findByBookid(int bookId);
	UserWishBookList findByUseridAndBookid(int userId,int bookId);
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description,userWishBookList.id,book.isbncode  from Book book " + 
			"inner join  UserWishBookList userWishBookList " + 
			"	on book.bookId=userWishBookList.bookid " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			"inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			" where userWishBookList.userid = ?1  " + 
			"AND book.isBookBorrowed = 0 AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> bookWishLists(int userId);
	
	@Query("SELECT userWishBookList.bookid,count(rating.ratings) from Rating rating "
			+ " INNER JOIN  UserWishBookList userWishBookList "
			+ " ON userWishBookList.bookid = rating.bookId ")
	     List<Object[]> getRatings();

	
	 
}
