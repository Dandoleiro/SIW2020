package it.uniroma3.siw.giugno20.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.giugno20.model.Comment;
import it.uniroma3.siw.giugno20.model.Task;
import it.uniroma3.siw.giugno20.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	@Transactional
	public Comment save(Comment comment) {
		return this.commentRepository.save(comment);
	}
	
	@Transactional
	public void addCommentToTask(Task task,Comment comment) {
		task.addComment(comment);
		this.commentRepository.save(comment);
	}
	
	@Transactional
	public Comment getComment(Long id) {
		Optional<Comment> result = this.commentRepository.findById(id);
		
		return result.orElse(null);
	}
	
	
}
