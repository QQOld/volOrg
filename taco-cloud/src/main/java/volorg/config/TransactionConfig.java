package volorg.config;

import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {
	
	@Bean(initMethod = "init", destroyMethod = "close")
  public UserTransactionManager userTransactionManager() throws SystemException {
      UserTransactionManager userTransactionManager = new UserTransactionManager();
     
      return userTransactionManager;
  }
	
	@Bean
	public UserTransaction userTransaction() {
	    return new UserTransactionImp();
	}

	@Bean
  public JtaTransactionManager transactionManager() throws SystemException {
      JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
      jtaTransactionManager.setTransactionManager(userTransactionManager());
      jtaTransactionManager.setUserTransaction(userTransaction());
      
      return jtaTransactionManager;
  }
}
