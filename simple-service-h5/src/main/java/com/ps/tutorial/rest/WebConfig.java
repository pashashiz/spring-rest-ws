package com.ps.tutorial.rest;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.ps.tutorial.rest.controllers"})
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        // Configure custom content negotiation strategy (for json, xml and so on)
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //Json already enabled by default
    }

    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MSSQLServer");
        dataSource.setInitialSize(1);
        dataSource.setMaxActive(4);
        return dataSource;
    }


    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.FORMAT_SQL, "false");
        properties.put(AvailableSettings.SHOW_SQL, "false");
        properties.put(AvailableSettings.HBM2DDL_AUTO, "create-drop");
        return properties;
    }

    @Bean
    public SessionFactory sessionFactory() throws PropertyVetoException {
        return new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("com.ps.tutorial.rest.data")
                .addProperties(hibernateProperties())
                .buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager txManager() throws PropertyVetoException {
        return new HibernateTransactionManager(sessionFactory());
    }
}
