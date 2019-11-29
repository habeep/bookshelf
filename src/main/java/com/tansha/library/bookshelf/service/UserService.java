package com.tansha.library.bookshelf.service;


import com.tansha.library.bookshelf.model.User;

import com.tansha.library.bookshelf.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmailId(email);
    }
    
    /* public String getPassword(String email) {
         User usr = userRepository.findByEmailId(email);
         return bCryptPasswordDecoder(usr.getPassword()
    } */
    
    
    
	public Optional findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}


    public User saveUser(User user) {
    	//System.out.println("User Password at userService >>> " + user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setIsActive(1);
        //Role userRole = roleRepository.findByRole("ADMIN");
       // user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    
    public User updateUser(User user) {
    	//System.out.println("User Object Value in updateUser() >>> "+user.toString());
        user.setIsActive(1);
        
        //Role userRole = roleRepository.findByRole("ADMIN");
       // user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

}