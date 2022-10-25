package volorg.config;

import java.util.HashMap;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
    basePackages = "volorg.repositories", 
    entityManagerFactoryRef = "dbOneEntityManager", 
    transactionManagerRef = "transactionManager"
)
public class DatabaseOneConfig {
	@Autowired
  private Environment env;
  
  @Bean
  public LocalContainerEntityManagerFactoryBean dbOneEntityManager() {
      LocalContainerEntityManagerFactoryBean em
        = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dbOneDataSource1());
      em.setPackagesToScan(
        new String[] { "volorg.models" });

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
  @ConfigurationProperties(prefix="spring.datasource")
  public AtomikosDataSourceBean dbOneDataSource1() {
  	Properties p = new Properties();
    p.setProperty("url", env.getProperty("spring.datasource.url"));
    p.setProperty("user", env.getProperty("spring.datasource.username"));
    p.setProperty("password", env.getProperty("spring.datasource.password"));
    AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
    atomikosDataSourceBean.setUniqueResourceName("volorg");
    atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
    atomikosDataSourceBean.setXaProperties(p);
    atomikosDataSourceBean.setBorrowConnectionTimeout(5);
    atomikosDataSourceBean.setMaxPoolSize(5);
    return atomikosDataSourceBean;
  }
  
//  @Primary
//  @Bean
//  @ConfigurationProperties(prefix="spring.datasource")
//  public DataSource dbOneDataSource() {
//  	return DataSourceBuilder
//  			.create()
//  			.username(env.getProperty("spring.datasource.username"))
//  			.password(env.getProperty("spring.datasource.password"))
//  			.url(env.getProperty("spring.datasource.url"))
//  			.driverClassName(env.getProperty("spring.datasource.driver-class-name"))
//  			.build();
//  }

//  @Primary
//  @Bean
//  public PlatformTransactionManager dbOneTransactionManager() {
//
//      JpaTransactionManager transactionManager
//        = new JpaTransactionManager();
//      transactionManager.setEntityManagerFactory(
//      		dbOneEntityManager().getObject());
//      return transactionManager;
//  }
}
