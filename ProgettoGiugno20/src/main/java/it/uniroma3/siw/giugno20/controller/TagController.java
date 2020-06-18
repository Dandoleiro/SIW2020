package it.uniroma3.siw.giugno20.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.giugno20.controller.validator.TagValidator;
import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.Tag;
import it.uniroma3.siw.giugno20.model.Task;
import it.uniroma3.siw.giugno20.service.ProjectService;
import it.uniroma3.siw.giugno20.service.TagService;
import it.uniroma3.siw.giugno20.service.TaskService;

@Controller
public class TagController {

	
	 @Autowired
	 TagService tagService;
	 
	 @Autowired
	 ProjectService projectService;
	 
	 @Autowired
	 TagValidator tagValidator;
	 
	 @Autowired
	 TaskService taskService;
	 

	 @RequestMapping(value = {"/projects/{projectId}/tag/add/form"},method = RequestMethod.POST)
	 public String showTagProjectForm(@PathVariable Long projectId, Model model) {
		 
		 Project project =this.projectService.getProject(projectId);
		 model.addAttribute("tagForm", new Tag());
		 model.addAttribute("project", project);
		 
		 return "newTagProject";
	 }
	 
	 @RequestMapping(value = {"/projects/{projectId}/tag/add"},method =RequestMethod.POST)
	 public String addTagToProject(@Valid @ModelAttribute("tagForm") Tag tag, BindingResult tagBinding,   @PathVariable Long projectId, Model model) {
		 
		 Project project=this.projectService.getProject(projectId);
		 this.tagValidator.validate(tag,tagBinding);
		 model.addAttribute("project",project);
		 if(!tagBinding.hasErrors()) {
			this.tagService.addTagToProject(project, tag);
			return "redirect:/projects/"+projectId;
		 }
			 
		 
		 return "newTagProject";
		 
	 }
	 
	 @RequestMapping(value = {"/projects/{projectId}/task/{taskId}/tag/add/form"},method = RequestMethod.POST)
	 public String showTagTaskForm(@PathVariable Long projectId,@PathVariable Long taskId, Model model) {
		 
		 Project project =this.projectService.getProject(projectId);
		 Task task =this.taskService.getTask(taskId);
		 model.addAttribute("tagForm", new Tag());
		 model.addAttribute("project", project);
		 model.addAttribute("task", task);
		 
		 
		 return "newTagTask";
	 }
	 
	 @RequestMapping(value = {"/projects/{projectId}/task/{taskId}/tag/add"},method =RequestMethod.POST)
	 public String addTagToTask(@Valid @ModelAttribute("tagForm") Tag tag, BindingResult tagBinding,  
			 					@PathVariable Long projectId, @PathVariable Long taskId, Model model) {
		 
		 Task task = this.taskService.getTask(taskId);
		 Project project=this.projectService.getProject(projectId);
		 model.addAttribute("task",task);
		 model.addAttribute("project",project);
		 this.tagValidator.validate(tag,tagBinding);
		 if(!tagBinding.hasErrors()) {
			this.tagService.addTagToTask(task, tag);
			return "redirect:/projects/"+projectId+"/task/"+taskId;
		 }
			 
		 
		 return "newTagTask";
	 }
		 
	 
	 
	 @RequestMapping(value = {"/projects/{projectId}/tag/{tagId}"},method = RequestMethod.GET)
	 public String showTag(@PathVariable Long tagId,Model model) {
		
		 Tag tag = this.tagService.getTag(tagId);
		 model.addAttribute("tag",tag);
		 
		 return "tag";
		 
	 }
}
