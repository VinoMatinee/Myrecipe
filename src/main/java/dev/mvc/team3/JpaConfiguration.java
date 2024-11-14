//package dev.mvc.team3;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"dev.jpa.issue.domain"})
//@EntityScan(basePackages = {"dev.jpa.issue.domain"})
//public class JpaConfiguration {
//    
//    @Autowired
//    private DataSource dataSource;
//    
//    @Bean
//    @ConfigurationProperties(prefix = "spring.jpa")
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("dev.jpa.issue.domain");
//        return em;
//    }
//    
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }
//}
