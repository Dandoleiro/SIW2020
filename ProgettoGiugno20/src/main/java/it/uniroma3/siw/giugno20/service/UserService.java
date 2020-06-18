package it.uniroma3.siw.giugno20.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public User getUser(Long id) {
		
		Optional<User> result = this.userRepository.findById(id);
		return result.orElse(null);

	
	}
	
	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}
	
	@Transactional
	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}
	
	
	@Transactional
	public List<User> getAll(){
		
		List<User> users = new ArrayList<User>();
		Iterable<User> user = this.userRepository.findAll();
		
		for ( User u : user)
			users.add(u);
		
		return users;
	}

	public List<User> getMembers(Project p) {
		
		return this.userRepository.findByVisibleProject(p) ;
	}
	

}
