package com.tansha.library.bookshelf.service;


import com.tansha.library.bookshelf.model.Children;
import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.repository.ChildrenRepository;
import com.tansha.library.bookshelf.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userChildrenService")
public class UserChildrenService {

    private ChildrenRepository childrenRepository;
    
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserChildrenService(ChildrenRepository childrenRepository,
                       
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.childrenRepository = childrenRepository;
        
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Children findChildrenByEmail(String email) {
        return childrenRepository.findByEmailId(email);
    }

    public Children saveChildren(Children children) {
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userRepository.findByEmailId(auth.getName());
    	//int userId = userRepository.findByName()auth.get;
    	children.setUserId(user.getId());
        children.setIsActive(1);
        //Role userRole = roleRepository.findByRole("ADMIN");
       // user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return childrenRepository.save(children);
    }

}