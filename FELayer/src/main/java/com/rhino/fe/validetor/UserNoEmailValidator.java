package com.rhino.fe.validetor;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.rhino.fe.model.GUIUserModel;

public class UserNoEmailValidator implements Validator{

	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		//just validate the Customer instances
		return GUIUserModel.class.isAssignableFrom(clazz);

	}

	public void validate(Object target, Errors errors) {
		
		GUIUserModel cust = (GUIUserModel)target;
		if(cust == null){
			return;
		}
		if(cust.getPassword() == null || cust.getConfirmPassword() == null ){
			cust.setFirstTime(true);
			return;
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
				"required.userName", "Field name is required.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"required.password", "Field name is required.");
			
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword",
				"required.confirmPassword", "Field name is required.");
		
		if(!(cust.getPassword().equals(cust.getConfirmPassword()))){
			errors.rejectValue("password", "notmatch.password");
		}
		
	}
	
}

