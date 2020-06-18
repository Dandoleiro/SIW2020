package it.uniroma3.siw.giugno20.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.giugno20.model.Credentials;
import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.service.CredentialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredentialsValidator implements Validator{
	
	@Autowired
	CredentialService credentialService;
	
	final Integer MIN_USERNAME_LENGTH = 3;
	final Integer MAX_USERNAME_LENGTH= 20;
	final Integer MIN_PASSWORD = 6;
	final Integer MAX_PASSWORD = 12;
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return User.class.equals(clazz);
	}
	
	
	@Override
	public void validate(Object target, Errors errors) {
		Credentials credentials = (Credentials)target;
		String username = credentials.getUsername().trim();
		String password = credentials.getPassword().trim();
		
		if(username.isEmpty()) {
			
			errors.rejectValue("username","required");
			
		}
		
			else if( username.length()<MIN_USERNAME_LENGTH || username.length()>MAX_USERNAME_LENGTH)
				errors.rejectValue("username","size");
			else if (this.credentialService.getCredential(username)!=null)
				errors.rejectValue("username", "duplicate");
		
		if(password.isEmpty()) {
			
			errors.rejectValue("password","required");
			
		}
		
			else if( password.length()<MIN_PASSWORD || password.length()>MAX_PASSWORD)
				errors.rejectValue("password","size");
		
		
	}
	
	
}
