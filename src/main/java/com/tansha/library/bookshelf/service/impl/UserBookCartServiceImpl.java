package com.tansha.library.bookshelf.service.impl;

import com.tansha.library.bookshelf.dao.UserBookCartDao;
import com.tansha.library.bookshelf.model.Book;
import com.tansha.library.bookshelf.model.UserBookCart;
import com.tansha.library.bookshelf.exceptions.Err;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tansha.library.bookshelf.service.UserBookCartService;

import java.util.List;

@Service
public class UserBookCartServiceImpl implements UserBookCartService {

    @Autowired
    UserBookCartDao userBookCartDao;

    /**
     * Adds the given book against the user to card
     *
     * @param ubc UserBookCart
     * @return true if add successful, false if failed
     */
    @Override
    public Err addUserBookToCart(UserBookCart ubc,int maxNumberofDeliveries) {
        if (ubc.getType_return() > 0) {
            return userBookCartDao.addUserBookToCartReturn(ubc);
        } else {
            return userBookCartDao.addUserBookToCartIssue(ubc,maxNumberofDeliveries);
        }
    }

    /**
     * To get the cart for a user
     *
     * @param userid int userid
     * @return List of UserBookCart
     */
    @Override
    public List<UserBookCart> getUserCart(int userid, boolean isTypeReturn) {
        if (isTypeReturn) {
            return userBookCartDao.getUserCartReturn(userid);
        } else {
            return userBookCartDao.getUserCartIssue(userid);
        }

    }

    /**
     * Clears the books for user from cart
     *
     * @param userid
     */
    @Override
    public void clearUserCart(int userid, boolean isTypeReturn) {
        List<UserBookCart> userBookCartList;
        if (isTypeReturn) {
            userBookCartList = userBookCartDao.getUserCartReturn(userid);
        } else {
            userBookCartList = userBookCartDao.getUserCartIssue(userid);
        }
        for (UserBookCart u : userBookCartList) {
            userBookCartDao.removeCartEntry(u);
        }
    }

    /**
     * @param userId
     * @param isTypeReturn
     * @return
     */
    @Override
    public List<Object[]> getUserBooks(int userId, boolean isTypeReturn) {
        List<Object[]> books;
        if (isTypeReturn) {
            books = userBookCartDao.getUserBooksInCartReturn(userId);
        } else {
            books = userBookCartDao.getUserBooksInCartIssue(userId);
        }
        return books;
    }

	@Override
	public void deleteUserBookFromCart(Integer userid,Integer bookId, boolean isTypeReturn) {
		
		 if (isTypeReturn) {
			 userBookCartDao.removeCartEntry( userBookCartDao.getUserBooksInCartReturnById(userid, bookId));
	        } else {
	        	userBookCartDao.removeCartEntry( userBookCartDao.getUserBooksInCartIssueById(userid, bookId));
	        }
	}

	@Override
	public void deleteUserBookFromCart(Integer userid,Integer bookId) {
		// TODO Auto-generated method stub
		
	}
    
    
   
}
