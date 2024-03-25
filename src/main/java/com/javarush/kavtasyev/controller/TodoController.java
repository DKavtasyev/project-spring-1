package com.javarush.kavtasyev.controller;

import com.javarush.kavtasyev.constants.Status;
import com.javarush.kavtasyev.entity.Task;
import com.javarush.kavtasyev.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TodoController
{
	private final TaskService taskService;
	
	@Autowired
	public TodoController(TaskService taskService)
	{
		this.taskService = taskService;
	}
	
	@GetMapping()
	public String index(@ModelAttribute("task") Task task, Model model)
	{
		model.addAttribute("tasks", taskService.getAllTasks());
		model.addAttribute("statuses", Status.values());
		return "index";
	}
	
	@PostMapping()
	public String save(@ModelAttribute("task") Task task)
	{
		taskService.saveTask(task);
		return "redirect:/task";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(Model model, @PathVariable("id") int id)
	{
		model.addAttribute("task", taskService.getTask(id));
		model.addAttribute("statuses", Status.values());
		return "edit";
	}
	
	@PatchMapping("/{id}")
	public String update(@ModelAttribute("task") Task task,
						 @PathVariable("id") int id)
	{
		taskService.updateTask(id, task);
		return "redirect:/task";
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") int id)
	{
		taskService.deleteTask(id);
		return "redirect:/task";
	}
}
