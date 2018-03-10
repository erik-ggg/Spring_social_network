package com.snetwork.validators;


import com.snetwork.entities.model.User;
import com.snetwork.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

import java.util.regex.Pattern;

@Component
public class SignUpFormValidator implements Validator {

	@Autowired
	private UsersService usersService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "Error.empty");

		if (user.getName().length() < 5 || user.getName().length() > 24) {
			errors.rejectValue("name", "Error.signup.name.length");
		}

		Pattern emailp = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		if (!emailp.matcher(user.getEmail()).find()) {
			errors.rejectValue("email", "Error.signup.email.format");
		}

		if (usersService.getUserByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "Error.signup.email.duplicate");
		}

		if (user.getPassword().length() < 5 || user.getPassword().length() > 24) {
			errors.rejectValue("password", "Error.signup.password.length");
		}

		if (!user.getPasswordConfirm().equals(user.getPassword())) {
			errors.rejectValue("passwordConfirm", 
					"Error.signup.passwordConfirm.coincidence");
		}
	}
}