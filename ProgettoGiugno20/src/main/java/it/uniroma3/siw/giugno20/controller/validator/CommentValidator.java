package it.uniroma3.siw.giugno20.controller.validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.giugno20.model.Comment;
import it.uniroma3.siw.giugno20.service.CommentService;

@Component
public class CommentValidator implements Validator {

	@Autowired
	CommentService commentService;
	
	final Integer MAX_COMMENT_LENGTH = 1000;

	@Override
	public boolean supports(Class<?> clazz) {
		return Comment.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Comment comment =(Comment)target;
		String commentString =comment.getTesto();
		
		if(commentString.isEmpty()) {

			errors.rejectValue("testo","required");

		}

		else if( commentString.length()>MAX_COMMENT_LENGTH)
			errors.rejectValue("testo","size");
		

	}



}
