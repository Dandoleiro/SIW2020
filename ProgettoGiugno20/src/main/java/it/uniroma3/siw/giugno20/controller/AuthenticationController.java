package it.uniroma3.siw.giugno20.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.giugno20.controller.session.SessionData;
import it.uniroma3.siw.giugno20.controller.validator.CredentialsValidator;
import it.uniroma3.siw.giugno20.controller.validator.UserValidator;
import it.uniroma3.siw.giugno20.model.Credentials;
import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.service.CredentialService;
import it.uniroma3.siw.giugno20.service.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UserValidator userValidator;
	@Autowired
	UserService userService;
	@Autowired
	CredentialsValidator credentialsValidator;
	@Autowired
	CredentialService credentialsService;
	
	@Autowired
	SessionData sessionData;
	
	
	@RequestMapping(value = {"/user/register"}, method = RequestMethod.GET )
	public String showRegisterForm(Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("credentialsForm",new Credentials());
		return "registerForm";
		
	}
	
	@RequestMapping(value= {"/user/register"}, method = RequestMethod.POST)
	public String registerForm(@Valid @ModelAttribute("userForm") User user,BindingResult userBinding, @Valid @ModelAttribute("credentialsForm")Credentials credentials,BindingResult credentialsBinding, Model model ) {
		
		this.userValidator.validate(user, userBinding);
		this.credentialsValidator.validate(credentials, credentialsBinding);
		if(!userBinding.hasErrors()&& !credentialsBinding.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "registrationSucces";
		}
		
			return "registerForm";
	}
	
	
	
	

}
