package it.uniroma3.siw.giugno20.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.Tag;
import it.uniroma3.siw.giugno20.model.Task;
import it.uniroma3.siw.giugno20.repository.TagRepository;

@Service
public class TagService {
	
	@Autowired
	TagRepository tagRepository;
	
	@Transactional
	public Tag save(Tag t) {
		return this.tagRepository.save(t);
	}
	
	@Transactional
	public List<Tag> getAll() {
		Iterable<Tag> tag = this.tagRepository.findAll();
		List<Tag> allTag = new ArrayList<>();
		
		for ( Tag t : tag)
			allTag.add(t);
	return allTag;
	}
	
	@Transactional
	public Tag getTag(Long id) {
		 Optional<Tag>result=this.tagRepository.findById(id);
	return result.orElse(null);
	}
	
	@Transactional
	public Tag addTagToProject(Project p,Tag t) {
		p.addTag(t);
		return this.tagRepository.save(t);
	}

	public Tag addTagToTask(Task task,  Tag tag) {
		tag.addTask(task);
		return this.tagRepository.save(tag);
		
	}
	
	public List<Tag> retriveTags (Task task){
		return this.tagRepository.findByTasks(task);
	}
		
}
