package com.tansha.library.bookshelf.validator;

import com.tansha.library.bookshelf.model.Children;
import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;

@Component
public class UserChildrenValidator implements Validator {
   

    @Override
    public boolean supports(Class<?> aClass) {
        return Children.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    	Children children = (Children) o;
       
       
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty");
        if(!errors.hasFieldErrors("firstName")) {

        if (children.getFirstName().length() < 6 || children.getFirstName().length() > 30) {
            errors.rejectValue("firstName", "Size.userForm.firstName");
        }
        }  
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty");
        if(!errors.hasFieldErrors("lastName")) {
	         if (children.getLastName().length() < 6 || children.getLastName().length() > 30) {
	            errors.rejectValue("lastName", "Size.userForm.lastName");
	        } 
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateOfBirth", "NotEmpty");
    }
}
