package com.tansha.library.bookshelf.model;

import javax.persistence.*;

/**
 * User Book Cart holds temporary cart for user who wants to issue the books
 */
@Entity
@Table(name = "user_books_wishlist")
public class UserWishBookList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "USER_ID")
    private int userid;

    @Column(name = "BOOK_ID")
    private int bookid;

   
    /**
     * THe default constructor.
     */
    public UserWishBookList() {
    }

    /**
     * @param user_id
     * @param book_id
     * @param type_return
     */
    public UserWishBookList(int user_id, int book_id, int type_return) {
        this.userid = user_id;
        this.bookid = book_id;
       
    }

    /**
     * Id Getter
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Id setter
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * User_Id setter
     *
     * @return user_id
     */
    public int getUser_id() {
        return userid;
    }

    /**
     * User_Id setter
     *
     * @param user_id
     */
    public void setUser_id(int user_id) {
        this.userid = user_id;
    }

    /**
     * Book_Id getter
     *
     * @return book_id
     */

    public int getBook_id() {
        return bookid;
    }

    /**
     * Book_Id setter
     *
     * @param book_id
     */
    public void setBook_id(int book_id) {
        this.bookid = book_id;
    }

   
}
