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

import it.uniroma3.siw.giugno20.controller.validator.CommentValidator;
import it.uniroma3.siw.giugno20.model.Comment;
import it.uniroma3.siw.giugno20.model.Project;
import it.uniroma3.siw.giugno20.model.Task;
import it.uniroma3.siw.giugno20.service.CommentService;
import it.uniroma3.siw.giugno20.service.ProjectService;
import it.uniroma3.siw.giugno20.service.TaskService;

@Controller
public class CommentController {

	@Autowired
	CommentService commentService;

	@Autowired
	TaskService taskService;
	@Autowired
	CommentValidator commentValidator;
	@Autowired
	ProjectService projectService;


	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/comment/add/form"}, method = RequestMethod.POST)
	public String showCommentForm(@PathVariable Long projectId, @PathVariable Long taskId,Model model) {
		Project project = this.projectService.getProject(projectId);
		Task task = this.taskService.getTask(taskId);
		model.addAttribute("project",project);
		model.addAttribute("task",task);
		model.addAttribute("commentForm",new Comment());

		return "newComment";
	}

	@RequestMapping(value = {"/projects/{projectId}/task/{taskId}/comment/add"}, method = RequestMethod.POST)
	public String addComment(@Valid @ModelAttribute("commentForm") Comment comment, BindingResult commentBinding,
			@PathVariable Long taskId,@PathVariable Long projectId, Model model) {

		Task task = this.taskService.getTask(taskId);
		Project project = this.projectService.getProject(projectId);
		model.addAttribute("project",project);
		model.addAttribute("task",task);
		this.commentValidator.validate(comment, commentBinding);
		if(!commentBinding.hasErrors()) {
			this.commentService.addCommentToTask(task, comment);
			;
			return "redirect:/projects/"+projectId+"/task/"+taskId;
		}
		return "newComment";
	}

}
