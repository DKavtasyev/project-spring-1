package com.javarush.kavtasyev.service;

import com.javarush.kavtasyev.entity.Task;

import java.util.List;


public interface TaskService
{
	Task getTask(int id);
	List<Task> getAllTasks();
	void saveTask(Task task);
	void updateTask(int id, Task task);
	void deleteTask(int id);
}
