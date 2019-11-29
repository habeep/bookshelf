package com.tansha.library.bookshelf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;


import com.tansha.library.bookshelf.model.BookBorrow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


//import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
//import org.springframework.data;
//import org.springframework.boot.autoconfigur
@Transactional
public interface BooksBorrowRepository extends CrudRepository<BookBorrow,String> {

	List<BookBorrow> findByUserId(int userId);
	
	
	List<BookBorrow> findByBookID(int bookId);
	List<BookBorrow> findBySlotID(int slotId);
	BookBorrow findByUserIdAndBookID(int userId,int bookId);
	
	/*@Modifying
	@Transactional
	@Query( "UPDATE BookBorrow bookborrow SET bookborrow.bookReturnStatus=1,bookborrow.actualDateofReturned=CURRENT_DATE() WHERE bookborrow.bookID=?1 AND bookborrow.userID=?2")
	public void updateUserBookReturnStatus(int userId, int bookId);
	*/
	

	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			//+ " INNER JOIN User user ON user.id=bookBorrow.userId "
			+ " INNER JOIN UserSubscription userSubscription ON userSubscription.userId=bookBorrow.userId "
			
			+ " WHERE    bookBorrow.userId=?1  " + 
			"  AND userSubscription.id = ?2  ")
	List<BookBorrow> getTotalBooksBorrows(int userId,int userSubcId);


	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " INNER JOIN UserSubscription usersubscription "
			+ "ON usersubscription.userId = bookBorrow.userId "
			+ " WHERE    bookBorrow.userId=?1 AND  usersubscription.id=?2" + 
			" AND YEAR(bookBorrow.requestedON) = YEAR(CURRENT_DATE()) group by bookBorrow.orderId")
	List<BookBorrow> getTotalDeliveries(int userId,int uSubcId);

	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND MONTH(bookBorrow.requestedON) = MONTH(CURRENT_DATE()) " + 
			" AND YEAR(bookBorrow.requestedON) = YEAR(CURRENT_DATE()) group by bookBorrow.orderId")
	List<BookBorrow> getCurrentMonthDeliveries(int userId);
	
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND bookBorrow.bookReturnStatus = 0 AND bookBorrow.bookID=?2 ")
	BookBorrow getBookBorrowsForUser(int userId,int bookId);
	
	
	//AND MONTH(bookBorrow.requestedON) = MONTH(CURRENT_DATE()) AND YEAR(bookBorrow.requestedON) = YEAR(CURRENT_DATE())
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE bookBorrow.userId=?1 AND bookBorrow.bookReturnStatus = 0 AND bookBorrow.type_return = 0  ")
	List<BookBorrow> getActiveBookBorrowsForUser(int userId);
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND bookBorrow.bookReturnStatus = 1 ")
	List<BookBorrow> getHistoryofBorrowsBooksListForUser(int userId);
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND MONTH(bookBorrow.requestedON) = MONTH(CURRENT_DATE()) " + 
			" AND YEAR(bookBorrow.requestedON) = YEAR(CURRENT_DATE())")
	List<BookBorrow> getCurrentMonthBookBorrowsListForUser(int userId);
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND MONTH(bookBorrow.requestedON) = MONTH(CURRENT_DATE()) GROUP BY bookBorrow.requestedON ")
	List<Object[]> getbookborrowDeliverySize(int userId);
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND bookBorrow.confirmBookReturnStatus = 0 AND bookBorrow.bookID=?2 AND bookBorrow.returnRequestedON IS NOT NULL ")
	List<Object[]> booksReturnStart(int userId, int bookId);
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND bookBorrow.bookReturnStatus = 1 AND bookBorrow.bookID=?2 AND bookBorrow.returnRequestedON IS NOT NULL ")
	List<Object[]> booksReturnSuccess(int userId, int bookId);
	
	@Query("SELECT bookBorrow from BookBorrow bookBorrow "
			+ " WHERE    bookBorrow.userId=?1 AND bookBorrow.bookReturnStatus = 0 AND bookBorrow.bookID=?2 AND bookBorrow.returnRequestedON IS NOT NULL ")
	List<Object[]> returnNotRequestedBooksList(int userId, int bookId);
	
	 
}
