package com.javarush.kavtasyev.dao;

import com.javarush.kavtasyev.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAOImpl implements TaskDAO
{
	private final SessionFactory sessionFactory;
	
	@Autowired												// Можно и без неё, но для чёткости добавил
	public TaskDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	public Task getTask(int id)
	{
		Session session = sessionFactory.getCurrentSession();
		return session.get(Task.class, id);
	}
	
	@Override
	public List<Task> getAllTasks()
	{
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("from Task", Task.class).list();
	}
	
	@Override
	public void saveTask(Task task)
	{
		Session session = sessionFactory.getCurrentSession();
		session.merge(task);
	}
	
	@Override
	public void deleteTask(int id)
	{
		Session session = sessionFactory.getCurrentSession();
		Task task = session.get(Task.class, id);
		session.remove(task);
	}
}
