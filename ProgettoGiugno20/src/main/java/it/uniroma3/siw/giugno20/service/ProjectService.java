package it.uniroma3.siw.giugno20.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.repository.ProjectRepository;

@Service
public class ProjectService {
	@Autowired
	ProjectRepository projectRepository;
	
	
	@Transactional
	public List<Project> retriveProjectOwnedBy(User user){
		return this.projectRepository.findByOwner(user);
		
	}
	
	@Transactional
	public void saveProject(Project p) {
		this.projectRepository.save(p);
	}
	
	@Transactional
	public Project addMembers(Project p , User m) {
		p.addMember(m);
		return this.projectRepository.save(p);
	}
	
	@Transactional
	public void deleteProject (Long id) {
		this.projectRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteByName(String name) {
		this.projectRepository.deleteByName(name);
	}
	
	@Transactional
	public Project getProject(Long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public List<Project> retriveVisibleProject(User user){
		return this.projectRepository.findByMembers(user);
	}
	
}
