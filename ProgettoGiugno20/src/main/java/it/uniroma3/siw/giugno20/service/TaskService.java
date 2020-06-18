package it.uniroma3.siw.giugno20.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.Task;
import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.repository.TaskRepository;

@Service
public class TaskService {
	@Autowired
	TaskRepository taskRepository;
	
	@Transactional
	public Task addTaskToUser(User u, Task t) {
		u.addTask(t);
		return this.taskRepository.save(t);
	}
	
	@Transactional
	public void deleteTask(Task t) {
		this.taskRepository.delete(t);
	}
	
	@Transactional
	public void deleteTaskById(Long id) {
		this.taskRepository.deleteById(id);
	}
	
	@Transactional
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
	}

	@Transactional
	public Task addTaskToProject(Project p , Task t) {
		p.addTask(t);
		
		return this.taskRepository.save(t);
	}
	@Transactional
	public Task getTask(Long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public List<Task> retriveVisibleTask(User user){
		return this.taskRepository.findByUserTask(user);
	}
}
