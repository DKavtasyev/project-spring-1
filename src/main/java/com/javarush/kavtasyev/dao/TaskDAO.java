package com.javarush.kavtasyev.dao;

import com.javarush.kavtasyev.entity.Task;

import java.util.List;

public interface TaskDAO
{
	Task getTask(int id);
	List<Task> getAllTasks(Integer pageNumber);
	void saveTask(Task task);
	void deleteTask(int id);
	int getNumberOfPages();
}
