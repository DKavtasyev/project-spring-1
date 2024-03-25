package com.javarush.kavtasyev.dao;

import com.javarush.kavtasyev.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Objects.isNull;

@Repository
@PropertySource("classpath:database.properties")
public class TaskDAOImpl implements TaskDAO
{
	private final SessionFactory sessionFactory;
	@Value("${hibernate.pageSize}")
	private int pageSize;
	
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
	public List<Task> getAllTasks(Integer pageNumber)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<Task> query = session.createQuery("from Task", Task.class);
		pageNumber = isNull(pageNumber) ? 0 : pageNumber - 1;
		query.setFirstResult(pageSize * pageNumber);
		query.setMaxResults(pageSize);
		return query.list();
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
	
	@Override
	public int getNumberOfPages()
	{
		Session session = sessionFactory.getCurrentSession();
		Query<Long> query = session.createQuery("select count(id) from Task", Long.class);
		long n = query.uniqueResult() / pageSize + 1;
		return (int) n;
	}
}
