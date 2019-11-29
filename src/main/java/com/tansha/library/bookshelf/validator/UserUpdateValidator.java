package com.tansha.library.bookshelf.validator;

import com.tansha.library.bookshelf.model.User;
import com.tansha.library.bookshelf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
		/*if (!errors.hasFieldErrors("name")) {
			if (user.getName().length() < 6 || user.getName().length() > 30) {
				errors.rejectValue("name", "Size.userForm.firstName");
			}
		}*/
		 if (user.getFlag() == 0) {
	            errors.rejectValue("flag", "terms.notaccepted");
		 }

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "houseNumber", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "locality", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "landmark", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pincode", "NotEmpty");
		
		if (!errors.hasFieldErrors("pincode")) {
			if (String.valueOf(user.getPincode()).length() == 0) {
				errors.rejectValue("pincode", "Size.userForm.pincode");
			} else if(String.valueOf(user.getPincode()).equals("0") || String.valueOf(user.getPincode()).equals("")) {
				errors.rejectValue("pincode", "Size.userForm.pincode");
			} else if (String.valueOf(user.getPincode()).length() < 6) {
				errors.rejectValue("pincode", "Size.userForm.pincode");
			}
		}

		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "NotEmpty");
		//System.out.println("User Phone number="+user.getPhoneNumber()+"=");
		if( String.valueOf(user.getPhoneNumber()).equals("0") || String.valueOf(user.getPhoneNumber()).equals("") ) {
			errors.rejectValue("phoneNumber", "NotEmpty");
		} else if( String.valueOf(user.getPhoneNumber()).length() <= 0 ) {
			errors.rejectValue("phoneNumber", "NotEmpty");
		} else {
			
			String telephoneNr = String.valueOf(user.getPhoneNumber());
		
			boolean isValidPhone = isValidPhoneNumber(telephoneNr);
		
			if (!isValidPhone) {
				errors.rejectValue("phoneNumber", "Size.userForm.phoneNumber");
			} 
		}
		
 	}
	
	public boolean isValidPhoneNumber(String phoneNo) {
        // First validate that the phone number is not null and has a length of 8
        if (null == phoneNo || phoneNo.length() != 10) {
            return false;
        }
        // Next check the first character of the string to make sure it's an 8 or 9
        //if (phoneNo.charAt(0) != '8' && phoneNo.charAt(0) != '9') {
          //  return false;
        //}
        // Now verify that each character of the string is a digit
        for (char c : phoneNo.toCharArray()) {
            if (!Character.isDigit(c)) {
                // One of the characters is not a digit (e.g. 0-9)
                return false;
            }
        }
        // At this point you know it is valid
        return true;
    }
	
}
