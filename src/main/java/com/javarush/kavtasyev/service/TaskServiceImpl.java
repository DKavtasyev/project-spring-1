package com.javarush.kavtasyev.service;

import com.javarush.kavtasyev.dao.TaskDAO;
import com.javarush.kavtasyev.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService
{
	private final TaskDAO taskDAO;
	
	@Autowired
	public TaskServiceImpl(TaskDAO taskDAO)
	{
		this.taskDAO = taskDAO;
	}
	
	
	@Override
	public Task getTask(int id)
	{
		return taskDAO.getTask(id);
	}
	
	@Override
	public List<Task> getAllTasks(Integer pageNumber)
	{
		return taskDAO.getAllTasks(pageNumber);
	}
	
	@Transactional
	@Override
	public void saveTask(Task task)
	{
		taskDAO.saveTask(task);
	}
	
	@Transactional
	@Override
	public void updateTask(int id, Task task)
	{
		task.setId(id);
		taskDAO.saveTask(task);
	}
	
	@Transactional
	@Override
	public void deleteTask(int id)
	{
		taskDAO.deleteTask(id);
	}
	
	@Override
	public int getNumberOfPages()
	{
		return taskDAO.getNumberOfPages();
	}
}
