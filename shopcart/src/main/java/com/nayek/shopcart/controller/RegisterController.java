package com.nayek.shopcart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nayek.shopcart.user.User;
import com.nayek.shopcart.user.UserService;
import com.nayek.shopcart.user.UserValidator;

@Controller
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	private UserValidator userValidator;
	private UserService userService;

	@Autowired
	public RegisterController(UserValidator userValidator, UserService userService) {
		this.userService = userService;
		this.userValidator = userValidator;
	}

	@GetMapping("/register")
	public String registration(Model model) {

		model.addAttribute("userForm", new User());
		return "register";
	}
	
	@PostMapping("register")
	public String registration(@ModelAttribute("userForm") User userForm , BindingResult bindingResult)
	{
		
		userValidator.validate(userForm, bindingResult);
		if(bindingResult.hasErrors())
		{
			logger.error(String.valueOf(bindingResult.getFieldError()));
			return "register";
		}
		
		userService.save(userForm);
		userService.login(userForm.getUsername(), userForm.getConfirmPassword());
		
		return "redirect:/home";
	}

}
