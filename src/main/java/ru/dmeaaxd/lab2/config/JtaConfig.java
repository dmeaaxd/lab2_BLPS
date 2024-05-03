package ru.dmeaaxd.lab2.config;

import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class JtaConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public UserTransactionManager userTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }

    @Bean(initMethod = "init", destroyMethod = "shutdownForce")
    public UserTransactionServiceImp userTransactionServiceImp() {
        Properties properties = new Properties();
        properties.setProperty("com.atomikos.icatch.service", "com.atomikos.icatch.standalone.UserTransactionServiceFactory");
        properties.setProperty("com.atomikos.icatch.log_base_dir", "./transaction-logs");
        properties.setProperty("com.atomikos.icatch.max_actives", "50");
        return new UserTransactionServiceImp(properties);
    }

    @Bean
    public UserTransactionManager userTransaction() throws Throwable {
        return userTransactionManager();
    }
}
