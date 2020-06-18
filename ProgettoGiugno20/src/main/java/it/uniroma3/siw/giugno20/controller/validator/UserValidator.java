package it.uniroma3.siw.giugno20.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.service.UserService;

@Component
public class UserValidator implements Validator {

	
	@Autowired
	UserService userService;
	
	final Integer MIN_FIRSTNAME_LENGTH = 3;
	final Integer MAX_FIRSTNAME_LENGTH= 20;
	final Integer MIN_LASTNAME_LENGTH = 3;
	final Integer MAX_LASTNAME_LENGTH= 20;
	
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return User.class.equals(clazz);
	}
	
	
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		String firstName = user.getFirstName().trim();
		String lastName = user.getLastName().trim();
		
		if(firstName.isEmpty()) {
			
			errors.rejectValue("firstName","required");
			
		}
		
			else if( firstName.length()<MIN_FIRSTNAME_LENGTH || firstName.length()>MAX_FIRSTNAME_LENGTH)
				errors.rejectValue("firstName","size");
			
		
		if(lastName.isEmpty()) {
				
			errors.rejectValue("lastName","required");
				
		}
			
			else if( lastName.length()<MIN_LASTNAME_LENGTH || lastName.length()>MAX_LASTNAME_LENGTH)
				errors.rejectValue("lastName","size");
				
	
}

}
