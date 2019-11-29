package com.tansha.library.bookshelf.controllers;

import javax.validation.Valid;

import com.tansha.library.bookshelf.validator.UserChildrenValidator;
import com.tansha.library.bookshelf.validator.UserValidator;
import com.tansha.library.bookshelf.model.Children;
import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.service.UserChildrenService;
import com.tansha.library.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChildrenController {

    @Autowired
    private UserChildrenService userChildrenService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserChildrenValidator userChildrenValidator;

    
    @RequestMapping(value="/user/children/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
    	
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	 User user = userService.findUserByEmail(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        Children children = new Children();
        modelAndView.addObject("userId", user.getId());
        modelAndView.addObject("children", children);
        modelAndView.setViewName("user/managechildren");
        return modelAndView;
    }
 

    @RequestMapping(value = "/user/children/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Children children, BindingResult bindingResult) {
    	 userChildrenValidator.validate(children, bindingResult);
        ModelAndView modelAndView = new ModelAndView();
        Children userExists = userChildrenService.findChildrenByEmail(children.getEmailId());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/managechildren");
        } else {
            userChildrenService.saveChildren(children);
            modelAndView.addObject("successMessage", "Children has been created successfully");
            modelAndView.addObject("children", new Children());
            modelAndView.setViewName("user/managechildren");

        }
        return modelAndView;
    }
}
