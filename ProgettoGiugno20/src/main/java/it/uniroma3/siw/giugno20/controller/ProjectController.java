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
import it.uniroma3.siw.giugno20.controller.validator.ProjectValidator;
import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.Task;
import it.uniroma3.siw.giugno20.model.User;
import it.uniroma3.siw.giugno20.service.CredentialService;
import it.uniroma3.siw.giugno20.service.ProjectService;
import it.uniroma3.siw.giugno20.service.TaskService;
import it.uniroma3.siw.giugno20.service.UserService;

@Controller
public class ProjectController {

	@Autowired
	SessionData sessionData;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CredentialService credentialService;
	@Autowired
	TaskService taskService;
	
	
	@RequestMapping(value = {"/projects/add"},method = RequestMethod.GET)
	public String showProjectForm (Model model) {
		
		model.addAttribute("projectForm", new Project() );
		
	return "NewProject";
	}
	
	
	
	@RequestMapping(value= {"/projects/add"}, method = RequestMethod.POST)
	public String CreateProject(@Valid @ModelAttribute("projectForm") Project project,BindingResult projectBinding, Model model ) {
		
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser",loggedUser);
		
		this.projectValidator.validate(project, projectBinding);
		if(!projectBinding.hasErrors()) {
			project.setOwner(loggedUser);
			projectService.saveProject(project);
			
			return "redirect:/projects/"+project.getId();
			
		}
		
			return "NewProject";
	}
	
	@RequestMapping(value = {"/projects"},method = RequestMethod.GET )
	public String viewProject (Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project>projects= projectService.retriveProjectOwnedBy(loggedUser);
		
		
		model.addAttribute("projectsList",projects);
		
		
		return "projects";
	}
	
	
	@RequestMapping(value= {"/projects/{projectId}"},method = RequestMethod.GET)
	public String project (Model model, @PathVariable Long projectId) {
		User loggedUser = this.sessionData.getLoggedUser();
		Project project = this.projectService.getProject(projectId);
		
		if(project==null)
			return"redirect:/projects";
		List<Task> assignedTasks = this.taskService.retriveVisibleTask(loggedUser);
		List<User> members=this.userService.getMembers(project);
		if(!project.getOwner().equals(loggedUser)&& !members.contains(loggedUser))
			return "redirect:/myProjects";
		model.addAttribute("project",project);
		model.addAttribute("members",members);
		model.addAttribute("loggedUser",loggedUser);
		model.addAttribute("assignedTasks",assignedTasks);
		return "project";
	}
	
	
	@RequestMapping(value = {"/sharedProjects"},method = RequestMethod.GET )
	public String shared (Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> sharedProjects = this.projectService.retriveVisibleProject(loggedUser);
		model.addAttribute("sharedProjects",sharedProjects);
		return "sharedProjects";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/users"},method = RequestMethod.POST )
	public String showShareUser(@PathVariable Long projectId,Model model ) {
		
		User loggedUser = this.sessionData.getLoggedUser();
		Project project = this.projectService.getProject(projectId);
		List<User> usersList = this.userService.getAll();
		usersList.remove(loggedUser);
		model.addAttribute("usersList",usersList );
		model.addAttribute("project",project);
		return "shareWith";
	}
		
	@RequestMapping(value = {"/projects/{projectId}/shareSuccess/{userId}"},method = RequestMethod.GET )
	public String shareProject(@PathVariable Long projectId,
							   @PathVariable Long userId,Model model) {
		
		User user =this.userService.getUser(userId);
		Project project = this.projectService.getProject(projectId);
		if(project.getMembers().contains(user))
			return "shareError";
		
		this.projectService.addMembers(project, user);
		model.addAttribute("user",user);
		return "shareSuccess";
	}
	
	@RequestMapping(value = {"/projects/{projectId}/delete"}, method = RequestMethod.POST)
	public String deleteProject(Model model, @PathVariable Long projectId) {
		
		this.projectService.deleteProject(projectId);
		
		return "redirect:/projects";
	
	}
	
	@RequestMapping(value = {"/projects/{projectId}/edit/form"}, method = RequestMethod.POST)
	public String showEditForm (@PathVariable Long projectId ,Model model ) {
		
		Project project = this.projectService.getProject(projectId);
		model.addAttribute("project",project);
		model.addAttribute("projectForm",project);
		
		return "editProject";
	}
	
	@RequestMapping(value= {"/projects/{projectId}/edit"}, method = RequestMethod.POST)
	public String editProject(@Valid @ModelAttribute("projectForm") Project project,BindingResult projectBinding,
						      @PathVariable Long projectId,Model model ) {
		
		Project updateProject = this.projectService.getProject(projectId);
		
		this.projectValidator.validate(project, projectBinding);
		if(!projectBinding.hasErrors()) {
			updateProject.setName(project.getName());
			this.projectService.saveProject(updateProject);
			
			return "redirect:/projects/"+projectId;
			
		}
		
			return "editProgect";
	}
	
}
