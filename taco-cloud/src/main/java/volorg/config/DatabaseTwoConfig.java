package volorg.config;

import java.util.HashMap;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
    basePackages = "volorg.repositories_log", 
    entityManagerFactoryRef = "dbTwoEntityManager", 
    transactionManagerRef = "transactionManager"
)
public class DatabaseTwoConfig {
	@Autowired
  private Environment env;
  
  @Bean
  public LocalContainerEntityManagerFactoryBean dbTwoEntityManager() {
      LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dbTwoDataSource1());
      em.setPackagesToScan(
        new String[] { "volorg.models_log" });

      HibernateJpaVendorAdapter vendorAdapter
        = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      HashMap<String, Object> properties = new HashMap<>();
      properties.put("hibernate.hbm2ddl.auto", "update");
      properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
      properties.put("hibernate.connection.autocommit", "false");
      properties.put("hibernate.transaction.coordinator_class", "jta");
      properties.put("hibernate.transaction.factory_class","org.hibernate.transaction.JTATransactionFactory");
      properties.put("javax.persistence.transactionType", "jta");
      em.setJpaPropertyMap(properties);
      em.afterPropertiesSet();

      return em;
  }
  
  @Bean(initMethod = "init", destroyMethod = "close")
  @ConfigurationProperties(prefix="spring.second-datasource")
  public AtomikosDataSourceBean dbTwoDataSource1() {
  	Properties p = new Properties();
    p.setProperty("url", env.getProperty("spring.second-datasource.url"));
    p.setProperty("user", env.getProperty("spring.second-datasource.username"));
    p.setProperty("password", env.getProperty("spring.second-datasource.password"));
    AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
    atomikosDataSourceBean.setUniqueResourceName("log");
    atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
    atomikosDataSourceBean.setXaProperties(p);
    atomikosDataSourceBean.setBorrowConnectionTimeout(5);
    atomikosDataSourceBean.setMaxPoolSize(5);
    return atomikosDataSourceBean;
  }

//  @Bean
//  @ConfigurationProperties(prefix="spring.second-datasource")
//  public DataSource dbTwoDataSource() {
//  	return DataSourceBuilder
//  			.create()
//  			.username(env.getProperty("spring.second-datasource.username"))
//  			.password(env.getProperty("spring.second-datasource.password"))
//  			.url(env.getProperty("spring.second-datasource.url"))
//  			.driverClassName(env.getProperty("spring.second-datasource.driver-class-name"))
//  			.build();
//  }

//  @Bean
//  public PlatformTransactionManager dbTwoTransactionManager() {
//
//      JpaTransactionManager transactionManager
//        = new JpaTransactionManager();
//      transactionManager.setEntityManagerFactory(
//      		dbTwoEntityManager().getObject());
//      return transactionManager;
//  }
}
