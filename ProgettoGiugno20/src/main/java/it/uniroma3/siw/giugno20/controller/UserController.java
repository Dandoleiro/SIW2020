package it.uniroma3.siw.giugno20.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
public class UserController {


	@Autowired
	SessionData sessionData;
	@Autowired
	CredentialService credentialService;
	@Autowired
	UserValidator userValidator;
	@Autowired
	UserService userService;
	@Autowired
	CredentialsValidator credentialsValidator;


	@RequestMapping (value = {"/me"}, method = RequestMethod.GET )
	public String me (Model model ) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("credentials",credentials);
		return "userProfile";
	}


	@RequestMapping (value = {"/home"}, method = RequestMethod.GET )
	public String home (Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		model.addAttribute("credentials",credentials);
		model.addAttribute("loggedUser",loggedUser);
		return "home";
	}

	@RequestMapping(value = {"/me/edit/form"}, method = RequestMethod.POST)
	public String showEditForm (Model model ) {

		User loggedUser = this.sessionData.getLoggedUser();
		Credentials updateCredentials = this.sessionData.getLoggedCredentials();
		model.addAttribute("userForm",loggedUser);
		model.addAttribute("user",loggedUser);
		model.addAttribute("credentialsForm",updateCredentials);
		model.addAttribute("credentials",updateCredentials);

		return "editProfile";
	}

	@RequestMapping(value= {"/me/edit"}, method = RequestMethod.POST)
	public String editUser(@Valid @ModelAttribute("userForm") User user,BindingResult userBinding,
							@Valid @ModelAttribute("credentialsForm") Credentials credentials,BindingResult credentialsBinding,
			Model model ) {

		User updateUser = this.sessionData.getLoggedUser();
		Credentials updateCredentials = this.sessionData.getLoggedCredentials();


		this.credentialsValidator.validate(credentials,credentialsBinding);
		this.userValidator.validate(user,userBinding);

		if(!userBinding.hasErrors()&&!credentialsBinding.hasErrors()) {
			updateUser.setFirstName(user.getFirstName());
			updateUser.setLastName(user.getLastName());

			updateCredentials.setUser(updateUser);
			updateCredentials.setUsername(credentials.getUsername());
			updateCredentials.setPassword(credentials.getPassword());

			this.credentialService.saveCredentials(updateCredentials);
			
			return "redirect:/me";
		}

		return "editProfile";

	}
	
	@RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
	public String showAdmin (Model model) {
		
		User admin=this.sessionData.getLoggedUser();
		model.addAttribute("user",admin);
		
		return "admin";
		
	}
	
	@RequestMapping(value = {"/admin/users"},method = RequestMethod.GET)
	public String showUserToDelete ( Model model ) {
		
		List<Credentials> credentials = this.credentialService.getAll();
		credentials.remove(this.sessionData.getLoggedCredentials());
		
		model.addAttribute("credentialsList", credentials);
		
		return"deleteUser";
		
	}
	
	@RequestMapping(value = {"/admin/users/{username}/delete"}, method = RequestMethod.POST)
	public String deleteUser (@PathVariable String username,Model model) {
		
		this.credentialService.deleteByUsername(username);
		
		return "redirect:/admin/users";
	}
	
	
	
}
