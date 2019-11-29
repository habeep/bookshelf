package com.tansha.library.bookshelf.repository;

import com.tansha.library.bookshelf.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailId(String emailId);
    public Optional<User> findByResetToken(String resetToken);
    
   /* @Query("SELECT ")
    Object getUserSubscriptions(int userId);*/
    
}  