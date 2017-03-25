package br.com.teste.cleber;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Main extends WebMvcConfigurerAdapter{

	private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		logger.info("Iniciando a aplicação!");
		SpringApplication.run(Main.class, args);
	}
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource driver = new DriverManagerDataSource();
		
		try {
			driver.setDriverClassName("com.mysql.jdbc.Driver");
			driver.setUrl("jdbc:mysql://localhost/eicon");
			driver.setUsername("root");
			driver.setPassword("root");
		} catch (Exception e) {
			logger.error("Erro ao construir 'DataSource': ", e);
		}
		
		return driver;
	}
	
	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory() {
	    return new HibernateJpaSessionFactoryBean();
	}
	
}
