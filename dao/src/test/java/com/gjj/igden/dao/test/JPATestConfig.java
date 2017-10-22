package com.gjj.igden.dao.test;

import org.apache.commons.dbcp.BasicDataSource;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gjj.igden.dao")
@ComponentScan({"com.gjj.igden.dao"})
@PropertySource("classpath:jdbcTemp.properties")
public class JPATestConfig {


    @Autowired
    Environment env;

    private static final String DRIVER_CLASS_NAME = "database.driver.classname";

    private static final String DATABASE_URL = "database.url";

    private static final String USERNAME = "database.username";

    private static final String PASSWORD = "database.password";

    private static final String HIBERNATE_AUTO_MODE = "hibernate.hbm2ddl.auto";

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";

    private static final String HIBERNATE_SHOW_QUERY = "hibernate.show_sql";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource datasource = new BasicDataSource();
        datasource.setDriverClassName(env.getProperty(DRIVER_CLASS_NAME));
        datasource.setUrl(env.getProperty(DATABASE_URL));
        datasource.setUsername(env.getProperty(USERNAME));
        datasource.setPassword(env.getProperty(PASSWORD));
        return datasource;
    }




    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[]{"com.gjj.igden.model"});

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }




    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty(HIBERNATE_AUTO_MODE));
        properties.setProperty("hibernate.dialect", env.getProperty(HIBERNATE_DIALECT));
        properties.setProperty("hibernate.show_sql", env.getProperty(HIBERNATE_SHOW_QUERY));
        return properties;
    }
}
