package com.tansha.library.bookshelf.dao;

import com.tansha.library.bookshelf.model.UserBookCart;
import com.tansha.library.bookshelf.repository.BookRepository;
import com.tansha.library.bookshelf.repository.UserBookCartRepository;
import com.tansha.library.bookshelf.exceptions.Err;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class UserBookCartDaoImpl implements UserBookCartDao {

    
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserBookCartRepository userBookCartRepository;
    
    /**
     * Adds the given book against the user to card
     *
     * @param ubc UserBookCart
     * @return Err false if add successful, true with error message if failed
     */
    @Override
    public Err addUserBookToCartIssue(UserBookCart ubc,int maxNumberofBooks) {
    	System.out.println("\n\n");
        List<UserBookCart> ubcList = this.getUserCartIssue(ubc.getUser_id());
        System.out.println("\n\n Cart size is >>>> " + ubcList.size()+"\n\n");
        if (ubcList.size() >= maxNumberofBooks) {
            return new Err(true, "You can only add "+ maxNumberofBooks+" books to the cart at a time");
        }
        for (UserBookCart u : ubcList) {
            if (u.getBook_id() == ubc.getBook_id()) {
                return new Err(true, "Book already added to cart!");
            }
        }
        userBookCartRepository.save(ubc);
        return new Err();
    }

    @Override
    public Err addUserBookToCartReturn(UserBookCart ubc) {
        List<UserBookCart> ubcList = this.getUserCartReturn(ubc.getUser_id());
        for (UserBookCart u : ubcList) {
            if (u.getBook_id() == ubc.getBook_id()) {
                return new Err(true, "Book already added to cart!");
            }
        }
        userBookCartRepository.save(ubc);
        return new Err();
    }

    /**
     * To get the cart for a user
     *
     * @param userid int userid
     * @return List of UserBookCart
     */
    @Override
    public List<UserBookCart> getUserCartIssue(int userid) {
        
        return  userBookCartRepository.getUserCartIssue(userid);
    }

    /**
     * To get the return cart for a user
     *
     * @param userid
     * @return
     */
    @Override
    public List<UserBookCart> getUserCartReturn(int userid) {
        
        return userBookCartRepository.getUserCartReturn(userid);
    }

    /**
     * @param ubc
     * @return
     */
    @Override
    public boolean removeCartEntry(UserBookCart ubc) {
    	userBookCartRepository.delete(ubc);
        return true;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Object[]> getUserBooksInCartIssue(int userId) {

        List<UserBookCart> ubcs = this.getUserCartIssue(userId);
        List<Object[]> books = new ArrayList<Object[]>();
        for (UserBookCart u : ubcs) {
        	books.addAll(bookRepository.bookDetails(u.getBook_id()));
        	
            //books.add(entityManager.find(Book.class, u.getBook_id()));

        }
        return books;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Object[]> getUserBooksInCartReturn(int userId) {
        List<UserBookCart> ubcs = this.getUserCartReturn(userId);
        List<Object[]> books = new ArrayList<Object[]>();
        for (UserBookCart u : ubcs) {
        	books.addAll(bookRepository.borrowBookDetails(u.getBook_id()));
           // books.add(entityManager.find(Book.class, u.getBook_id()));

        }
        return books;
    }
    
    /**
     * @param userId
     * @return
     */
    @Override
    public UserBookCart getUserBooksInCartIssueById(int userId,int bookId) {
//    	String query = "Select ubc From UserBookCart ubc WHERE ubc.user_id = :userid AND ubc.book_id = :bookid AND ubc.type_return = 0";
//        Query q = entityManager.createQuery(query, UserBookCart.class);
//        q.setParameter("userid", userId);
//        q.setParameter("bookid", bookId);
//        return (UserBookCart) q.getSingleResult();
    	//UserBookCart CartIssue =;
    	return  userBookCartRepository.getUserBooksInCartIssueById(userId,bookId,0);
    	
    	
    }

   /**
    * @param userId
    * @return
    */
   @Override
   public UserBookCart getUserBooksInCartReturnById(int userId,int bookId) {
   	
       return userBookCartRepository.getUserBooksInCartIssueById(userId,bookId,1);
   }
}
