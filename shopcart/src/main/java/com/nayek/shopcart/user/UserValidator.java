package com.nayek.shopcart.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

	private final UserService userService;

	@Autowired
	public UserValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		User user = (User) target;

		// username or password can't be empty or contain white spaces
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.not_empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");

		// username must have 2 to 30 characters
		if (user.getUsername().length() < 2) {
			errors.rejectValue("username", "register.error.username.less_2");
		}

		if (user.getUsername().length() > 30) {
			errors.rejectValue("username", "register.error.username.over_30");
		}

		// Username should be unique
		if (userService.findByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "register.error.duplicated.username");
		}

		// Email should be unique
		if (userService.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "register.error.duplicated.email");
		}

		// Password must have 8 to 30 characters
		if (user.getPassword().length() < 8) {
			errors.rejectValue("password", "register.error.password.less_8");
		}

		if (user.getPassword().length() > 32) {
			errors.rejectValue("password", "register.error.password.over_32");
		}

		// Password must be same as confirmPassword
		if (!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("confirmPassword", "register.error.diff_password");
		}

		// Age needs to be higher than 15
		if (user.getAge() <= 15) {
			errors.rejectValue("age", "register.error.age_size");
		}
	}

}
