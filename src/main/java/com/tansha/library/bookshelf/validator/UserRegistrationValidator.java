package com.tansha.library.bookshelf.validator;

import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;

@Component
public class UserRegistrationValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
       
       
      
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId", "NotEmpty");
        if (user.getEmailId().length() < 6 || user.getEmailId().length() > 32) {
            errors.rejectValue("emailId", "Size.userForm.emailId");
        }
        
        if (userService.findUserByEmail(user.getEmailId()) != null) {
            errors.rejectValue("emailId", "Duplicate.userForm.emailId");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmpassword", "NotEmpty");
        
        
        if(!user.getPassword().equals(user.getConfirmpassword())) {
        	 errors.rejectValue("confirmpassword", "message.mismatch.password");
        }
        
       
        
 
    }
}
