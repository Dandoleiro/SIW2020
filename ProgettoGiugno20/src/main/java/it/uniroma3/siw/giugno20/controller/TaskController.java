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
import it.uniroma3.siw.giugno20.controller.validator.TaskValidator;
import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.Tag;
import it.uniroma3.siw.giugno20.model.Task;
import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.service.ProjectService;
import it.uniroma3.siw.giugno20.service.TagService;
import it.uniroma3.siw.giugno20.service.TaskService;
import it.uniroma3.siw.giugno20.service.UserService;

@Controller
public class TaskController {

	@Autowired
	TaskService taskService;

	@Autowired
	SessionData sessionData;

	@Autowired
	ProjectService projectService;

	@Autowired
	TaskValidator taskValidator;
	
	@Autowired
	UserService userService;
	
	@Autowired
	TagService tagService;


	@RequestMapping(value = { "/projects/{projectId}/task/add/form" }, method = RequestMethod.POST)
	public String showTaskForm(Model model ,@PathVariable Long projectId ) {

		Project project=this.projectService.getProject(projectId);
		model.addAttribute("project",project);
		model.addAttribute("taskForm", new Task());
		return "newTask";
	}

	@RequestMapping(value = {"/projects/{projectId}/task/add"}, method = RequestMethod.POST)
	public String addTask(@Valid @ModelAttribute("taskForm") Task task, BindingResult taskBinding, 
			@PathVariable Long projectId, Model model) {

		Project project = this.projectService.getProject(projectId);
		model.addAttribute("project",project);
		this.taskValidator.validate(task, taskBinding);
		if(!taskBinding.hasErrors()) {
			
			this.taskService.addTaskToProject(project, task);
			
			return "redirect:/projects/"+project.getId();
		}

		return "newTask";
	}


	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}"}, method = RequestMethod.GET)
	public String showTask (@PathVariable Long projectId,@PathVariable Long taskId,Model model) {
		User loggedUser = this.sessionData.getLoggedUser();
		Task task = this.taskService.getTask(taskId);
		Project project = this.projectService.getProject(projectId);
		List<Tag> tags = this.tagService.retriveTags(task);
		
		model.addAttribute("task",task);
		model.addAttribute("project",project);
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("tags",tags);

		return "task";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/edit/form"}, method = RequestMethod.POST)
	public String showTaskForm (Model model,@PathVariable Long projectId, @PathVariable Long taskId) {
		
		Project project = this.projectService.getProject(projectId);
		Task task = this.taskService.getTask(taskId);
		
		model.addAttribute("project",project);
		model.addAttribute("taskForm",task);
		model.addAttribute("task",task);
		
		
		return "editTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/edit"}, method = RequestMethod.POST)
	public String editForm(@Valid @ModelAttribute("taskForm") Task task,BindingResult taskBinding,
						   @PathVariable Long projectId,@PathVariable Long taskId,Model model) {
		
		Task updateTask = this.taskService.getTask(taskId);
		Project project = this.projectService.getProject(projectId);
		model.addAttribute("task",updateTask);
		model.addAttribute("project",project);
		this.taskValidator.validate(task, taskBinding);
		
		if(!taskBinding.hasErrors()) {
			updateTask.setName(task.getName());
			updateTask.setDescription(task.getDescription());
		
		
			this.taskService.saveTask(updateTask);
			return "redirect:/projects/"+projectId;
		}
		
		return "editTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/delete"}, method = RequestMethod.POST)
	public String delete ( Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		
		this.taskService.deleteTaskById(taskId);
		
		
		return "redirect:/projects/"+projectId;
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/assign"},method = RequestMethod.POST )
	public String showUserToAssign(@PathVariable Long projectId,@PathVariable Long taskId,Model model ) {
		
		Project project = this.projectService.getProject(projectId);
		List<User> members=this.userService.getMembers(project);
		User loggedUser = this.sessionData.getLoggedUser();
		Task task = this.taskService.getTask(taskId);
		members.remove(loggedUser);
		model.addAttribute("members",members);
		model.addAttribute("project",project);
		model.addAttribute("task",task);
		return "assignTask";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/assignSuccess/{userId}"},method = RequestMethod.GET )
	public String taskAssigned(@PathVariable Long userId,
							   @PathVariable Long projectId,
							   @PathVariable Long taskId ,Model model) {
		Project project = this.projectService.getProject(projectId);
		User user =this.userService.getUser(userId);
		Task task = this.taskService.getTask(taskId);
		if(user.getActiveTasks().contains(task))
			return "assignTaskError";
		
		this.taskService.addTaskToUser(user, task);
		model.addAttribute("user",user);
		model.addAttribute("project",project);
		model.addAttribute("task",task);
		return "assignSuccess";
	}
		
}
