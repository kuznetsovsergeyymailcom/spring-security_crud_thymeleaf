package spring.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import spring.app.configuration.initializator.TestDataInit;
import spring.app.service.abstraction.RoleService;
import spring.app.service.abstraction.UserService;

import javax.sql.DataSource;

import java.util.Properties;

import static spring.app.utils.PropertiesReader.getProperties;

@Configuration
@EnableWebMvc
@ComponentScan("spring.app")
@EnableTransactionManagement
public class ApplicationContextConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(getDataSource());
		emf.setJpaProperties(getJpaProperties());
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setPackagesToScan("spring/app/models");
		return emf;
	}

	@Bean
	public DataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(getProperties("driver.class"));
		dataSource.setUrl(getProperties("connection.url"));
		dataSource.setUsername(getProperties("username"));
		dataSource.setPassword(getProperties("password"));
		return dataSource;
	}

	@Bean
	@SuppressWarnings(value = "all")
	public PlatformTransactionManager txManager(){
		return new JpaTransactionManager(getEntityManagerFactoryBean().getObject());
	}

	// инициализируем базу данных
	@Bean(initMethod = "init")
	@Autowired
	public TestDataInit initTestData(UserService userService, RoleService roleService) {
		return new TestDataInit(userService, roleService);
	}

	private Properties getJpaProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", getProperties("dialect"));
		properties.put("hibernate.show_sql", getProperties("show_sql"));
		properties.put("hibernate.hbm2ddl.auto", getProperties("hbm2ddl.auto"));
		return properties;
	}
}
