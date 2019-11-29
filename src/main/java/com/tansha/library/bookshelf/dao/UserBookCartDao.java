package com.tansha.library.bookshelf.dao;

import com.tansha.library.bookshelf.model.UserBookCart;
import com.tansha.library.bookshelf.exceptions.Err;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional

public interface UserBookCartDao {

    /**
     * Adds the given book against the user to cart for issue
     *
     * @param ubc UserBookCart
     * @return true if add successful, false if failed
     */
	Err addUserBookToCartIssue(UserBookCart ubc,int maxNumberofDeliveries);

    /**
     * Adds the given book against the user to cart for return
     * @param ubc
     * @return
     */
    Err addUserBookToCartReturn(UserBookCart ubc);

    /**
     * To get the issue cart for a user
     *
     * @param userid int userid
     * @return List of UserBookCart
     */
    List<UserBookCart> getUserCartIssue(int userid);

    /**
     * To get the return cart for a user
     *
     * @param userid
     * @return List of the user books cart.
     */
    List<UserBookCart> getUserCartReturn(int userid);

    /**
     *
     * @param ubc
     * @return Delete status of the cart.
     */
    boolean removeCartEntry(UserBookCart ubc);

    /**
     *
     * @param userId
     * @return The list of the books wrt to users which are in cart.
     */
    List<Object[]> getUserBooksInCartIssue(int userId);

    /**
     * @param userId
     * @return List of the user books in the cart.
     */
    List<Object[]> getUserBooksInCartReturn(int userId);
    
    UserBookCart getUserBooksInCartIssueById(int userId,int bookId);
    
    UserBookCart getUserBooksInCartReturnById(int userId,int bookId);
}