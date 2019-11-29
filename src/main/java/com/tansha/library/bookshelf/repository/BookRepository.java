package com.tansha.library.bookshelf.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;


import com.tansha.library.bookshelf.model.Book;
import com.tansha.library.bookshelf.model.BookCategory;
import com.tansha.library.bookshelf.model.Rating;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface BookRepository extends CrudRepository<Book,String> {	
	
	List<Book> findByBookTitle(String bookTitle);
	Book findByBookId(int bookId);
	List<Book> findByBookTitleOrIsbncode(String bookTitle,String bookISBN );
	List<Book> findByAuthorIDIn(List<Integer> authordIds); 
	List<Book> findByPublisherIDIn(List<Integer> publisherIds); 
	List<Book> findByLanguageIdIn(List<Integer> languageIds); 
	
	@Query("SELECT book from Book book where book.bookId = ?1 AND book.isBookBorrowed=0 AND book.isLost = 0")
	Book findOne(int bookId);
	//List<Book> findByBookTitleOrIsbncodeOrAuthorIDOrLanguageIdOrLongTitleOrPublisherIDOrPublisherID(String bookTitle,String bookISBN,int authorId,int languageId,String longTitle,int publisherId);
	List<Book> findByIsbncode(String isbnCode);
	List<Book> findByIsFeaturedBook(int featuredBookFlag);
	
	//SELECT book from Book book LEFT JOIN Author author ON book.authorId = author.authorId LEFT JOIN Publisher publisher ON book.publisherId=publisher.publisherId where book.isFeaturedBook = ?1 AND book.isBookBorrowed=0 GROUP BY book.isbncode
	
	@Query("SELECT author.authorName,publisher.publisherName,book.bookId,book.bookTitle,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,language.language from Book book, Author author,Publisher publisher,ReadingLevel readingLevels,Language language where book.publisherID=publisher.publisherId AND book.authorID = author.authorID AND book.readingLevelId = readingLevels.readingLevelId AND book.languageId=language.languageID AND book.isFeaturedBook = ?1 AND book.isBookBorrowed=0 AND book.isActive= 1 ")
	List<Object[]> getFeaturedBooks(int featuredBookFlag);
	
	//AND bookborrow.confirmBookReturnStatus = 1 
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,languages.language,book.noofPages,book.binding from Book book " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			//"inner join BookBorrow  bookborrow " + 
			//"	ON book.bookId = bookborrow.bookID " + 
			"inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			"inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			"inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			"where (book.longTitle like %?1% OR book.bookTitle like %?1% OR publisher.publisherName like %?1% OR readingLevels.readingLevel like %?1% OR book.publishedYear like %?1% " + 
			"OR authors.authorName like %?1% OR languages.language like %?1% OR bookCategory.categoryName like %?1% ) " + 
			"AND book.isBookBorrowed = 0 AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> searchBooks(String searchStr);
	
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,languages.language,book.noofPages,book.binding  from Book book " + 
			" inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " +
			//"inner join BookBorrow  bookborrow " + 
			//"	ON book.bookId = bookborrow.bookID " + 			
			" inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			" where ( book.publisherID = ?1 OR book.categoryID = ?2 OR book.languageId = ?3 OR  readingLevels.readingLevelId = ?4)" + 
			//"OR authors.authorName like %?1% OR languages.language like %?1% OR bookCategory.categoryName like %?1% ) " + 
			"AND book.isBookBorrowed = 0 AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> selectSearchBooks(int publisherId,int bookCategoryId,int languageId,int readingLevelList); 
	
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,languages.language,book.noofPages,book.binding  from Book book " + 
			" inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " +
			//"inner join BookBorrow  bookborrow " + 
			//"	ON book.bookId = bookborrow.bookID " + 
			" inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			//" where " + 
			//"OR authors.authorName like %?1% OR languages.language like %?1% OR bookCategory.categoryName like %?1% ) " + 
			" where book.isBookBorrowed = 0  AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> quickSearchBooks(); 
	
	//habeep
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,languages.language,book.noofPages,book.binding  from Book book " + 
			" inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			//"inner join BookBorrow  bookborrow " + 
			//"	ON book.bookId = bookborrow.bookID " + 			
			" inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			" where ( readingLevels.readingLevelId = ?1)" + 
			//"OR authors.authorName like %?1% OR languages.language like %?1% OR bookCategory.categoryName like %?1% ) " + 
			"AND book.isBookBorrowed = 0 AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> selectQuickReadingLevelSearchBooks(int readingLevelList); 
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,languages.language,book.noofPages,book.binding  from Book book " + 
			" inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " +
			//"inner join BookBorrow  bookborrow " + 
			//"	ON book.bookId = bookborrow.bookID " + 
			" inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			" where ( book.categoryID = ?1)" + 
			//"OR authors.authorName like %?1% OR languages.language like %?1% OR bookCategory.categoryName like %?1% ) " + 
			"AND book.isBookBorrowed = 0 AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> selectQuickCategorySearchBooks(int bookCategoryId); 
	
	// readingLevelList, publishersList, categoriesList, languageList 
	//publisherId,categoryId,languageId,readingLevelList
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description,book.noofPages,book.binding  from Book book " + 
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
			" where book.bookId = ?1  " + 
			"AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> bookDetails(int bookId);
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description,book.noofPages,book.binding   from Book book " + 
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
			" where book.bookId = ?1  " + 
			"AND book.isBookBorrowed = 1 AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ")
	List<Object[]> borrowBookDetails(int bookId);
	
	@Query("select count(bookId) from Book book where book.bookId =?1 AND book.isBookBorrowed = 1")
	Integer isBookBorrowed(int bookId);
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description  from Book book " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			"inner join  BookBorrow bookborrow " + 
			"	on bookborrow.bookID=book.bookId " + 
			"inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			//" where   " + 
			" WHERE bookborrow.userId = ?1 AND bookborrow.confirmBookReturnStatus = 0 AND book.isBookBorrowed = 1 AND book.isActive = 1 AND book.isLost = 0 AND bookborrow.deliveredOn IS NOT NULL group by book.isbncode ")
	List<Object[]> bookBorrowHistoryDetails(int userId);
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description  from Book book " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			"inner join  BookBorrow bookborrow " + 
			"	on bookborrow.bookID=book.bookId " + 
			"inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			" inner join UserBookCart ubc " +
			"   ON ubc.book_id = bookborrow.bookID " + 
			//" where   " + 
			" WHERE ubc.book_id= ?2 AND ubc.user_id = ?1 AND book.isBookBorrowed = 1 AND book.isActive = 1 AND book.isLost = 0 AND bookborrow.returnRequestedON IS NULL group by book.isbncode ")
	List<Object[]> bookBorrowHistoryDetailswithReturnInProgress(int userId,int bookId);
	
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description  from Book book " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			"inner join  BookBorrow bookborrow " + 
			"	on bookborrow.bookID=book.bookId " + 
			"inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
//			" inner join UserBookCart ubc " +
//			" ON ubc.book_id = bookborrow.bookID " + 
			//" where   " + 
			" WHERE bookborrow.bookID= ?2 AND bookborrow.userId = ?1 AND bookborrow.confirmBookReturnStatus = 0 AND bookborrow.bookReturnStatus = 1 AND book.isBookBorrowed = 1 AND book.isActive = 1 AND book.isLost = 0 AND bookborrow.type_return=1 AND bookborrow.returnRequestedON IS NOT NULL group by book.isbncode ")
	List<Object[]> bookBorrowHistoryDetailswithReturnRequested(int userId,int bookId);
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description,bookborrow.bookReturnStatus,bookborrow.requestedON  from Book book " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			"inner join  BookBorrow bookborrow " + 
			"	on bookborrow.bookID=book.bookId " + 
			"inner join Author  authors " + 	
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			//" where   " + 
			" WHERE bookborrow.userId = ?1 AND bookborrow.deliveredOn IS NOT NULL AND MONTH(bookborrow.requestedON) <= ( MONTH(CURRENT_DATE())  ) AND YEAR(bookborrow.requestedON) = YEAR(CURRENT_DATE()) AND book.isActive = 1 ")
	List<Object[]> bookBorrowHistoryDetail(int userId);
	/* AND bookborrow.requestedON >= DATE(NOW() - INTERVAL 3 MONTH)
	*/
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description,bookborrow.bookReturnStatus,bookborrow.requestedON  from Book book " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			"inner join  BookBorrow bookborrow " + 
			"	on bookborrow.bookID=book.bookId " + 
			"inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			//" where   " + 
			" WHERE bookborrow.userId = ?1 AND  MONTH(bookborrow.requestedON) = ?2 AND YEAR(bookborrow.requestedON) = ?3 AND book.isActive = 1 ")
	List<Object[]> bookBorrowHistoryArchiveDetail(int userId,int month,int year);
	
	
	@Query("SELECT book from Book book where (bookTitle = ?1 OR ISBNCODE = ?2) AND isBookBorrowed = 1")
	List<Book> findByBookTitleOrIsbncodeAndIsBookBorrowed(String bookTitle,String isbnCode);
	
	@Query("SELECT book from Book book where (bookTitle = ?1 OR ISBNCODE = ?2) AND isBookBorrowed = 0")
	List<Book> findByBookTitleOrIsbncodeAndIsBookNotBorrowed(String bookTitle,String isbnCode);
	//List<Book> findByISBNCODE(String isbnCode);
	
	@Query("select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,languages.language,book.isbncode,readingLevels.readingLevel,readingLevels.readingLevelId,book.longTitle,book.description  from Book book " + 
			"inner join  Publisher publisher " + 
			"	on book.publisherID=publisher.publisherId " + 
			"inner join  BookBorrow bookborrow " + 
			"	on bookborrow.bookID=book.bookId " + 
			"inner join Author  authors " + 
			"	ON book.authorID = authors.authorID " + 
			" inner join ReadingLevel  readingLevels " + 
			"	ON book.readingLevelId = readingLevels.readingLevelId " + 
			" inner join Language languages " + 
			"	on book.languageId = languages.languageID " +  
			" inner join BookCategory  bookCategory " + 
			"	ON bookCategory.categoryId = book.categoryID " + 
			//" where   " + 
			" WHERE bookborrow.userId = ?1 AND bookborrow.requestedON IS NOT NULL AND bookborrow.deliveredOn IS NULL AND book.isActive = 1 group by book.isbncode ")
	List<Object[]> getDeliveryInProgroseBooks(int userId);
	 
}
