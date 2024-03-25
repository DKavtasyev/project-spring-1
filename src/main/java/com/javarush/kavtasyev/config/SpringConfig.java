package com.javarush.kavtasyev.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.beans.PropertyVetoException;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan("com.javarush.kavtasyev")
@EnableWebMvc
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class SpringConfig implements WebMvcConfigurer
{
	private static final String DRIVER = "hibernate.datasource.driver";
	private static final String URL = "hibernate.datasource.url";
	private static final String USER = "hibernate.datasource.username";
	private static final String PASSWORD = "hibernate.datasource.password";
	private static final String DIALECT = "hibernate.dialect";
	private static final String SHOW_SQL = "hibernate.show_sql";
	public static final String HBM2DDL = "hibernate.hbm2ddl";
	
	private final ApplicationContext applicationContext;
	private final Environment environment;
	
	@Autowired
	public SpringConfig(ApplicationContext applicationContext, Environment environment)
	{
		this.applicationContext = applicationContext;
		this.environment = environment;
	}
	
	@Bean
	public SpringResourceTemplateResolver templateResolver()
	{
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine()
	{
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry)
	{
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		registry.viewResolver(resolver);
	}
	
	//****************************************************************************************************************//
	//											Конфигурация Hibernate												  //
	//****************************************************************************************************************//
	
	@Bean
	public ComboPooledDataSource dataSource() throws PropertyVetoException
	{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass(Objects.requireNonNull(environment.getProperty(DRIVER)));
		dataSource.setJdbcUrl(environment.getProperty(URL));
		dataSource.setUser(environment.getProperty(USER));
		dataSource.setPassword(environment.getProperty(PASSWORD));
		
		dataSource.setInitialPoolSize(2);
		dataSource.setMinPoolSize(2);
		dataSource.setMaxPoolSize(7);
		dataSource.setMaxIdleTime(600);
		return dataSource;
	}
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException
	{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.javarush.kavtasyev.entity");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	public HibernateTransactionManager transactionManager() throws PropertyVetoException
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
	
	
	private Properties hibernateProperties()
	{
		Properties properties = new Properties();
		properties.put(DIALECT, environment.getProperty(DIALECT));
		properties.put(SHOW_SQL, environment.getProperty(SHOW_SQL));
		properties.put(HBM2DDL, environment.getProperty(HBM2DDL));
		return properties;
	}
}

