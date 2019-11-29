package com.tansha.library.bookshelf.service;

import com.tansha.library.bookshelf.model.Book;
import com.tansha.library.bookshelf.model.UserBookCart;
import com.tansha.library.bookshelf.exceptions.Err;

import java.util.List;

public interface UserBookCartService {

    /**
     * Adds the given book against the user to card
     *
     * @param ubc UserBookCart
     * @return true if add successful, false if failed
     */
    public Err addUserBookToCart(UserBookCart ubc,int maxNumberofDeliveries);

    /**
     * To get the cart for a user
     *
     * @param userid int userid
     * @param isTypeReturn
     * @return List of UserBookCart
     */
    public List<UserBookCart> getUserCart(int userid, boolean isTypeReturn);

    /**
     * Clears the books for user from cart
     *
     * @param userid
     * @param isTypeReturn
     */
    void clearUserCart(int userid, boolean isTypeReturn);
    
    /**
     *  Clears the books for user from cart
    *
    * @param userid
    * @param isTypeReturn
    */
   void deleteUserBookFromCart(Integer userid,Integer bookId, boolean isTypeReturn);

   /**
    *  Clears the books for user from cart
   *
   * @param userid
   * @param isTypeReturn
   */
  void deleteUserBookFromCart(Integer userid,Integer bookId);
		  
    /**
     * @param userId
     * @param isTypeReturn
     * @return
     */
    List<Object[]> getUserBooks(int userId, boolean isTypeReturn);
}
