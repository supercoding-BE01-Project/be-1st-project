package com.be01.be_01_01.dashboard.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.be01.be_01_01.dashboard.repository.board","com.be01.be_01_01.dashboard.repository.comment",
                        "com.be01.be_01_01.dashboard.repository.user"},
        entityManagerFactoryRef = "entityManagerFactoryBean",
        transactionManagerRef = "tmJpa"
)
public class JpaCofig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.be01.be_01_01.dashboard.repository.board","com.be01.be_01_01.dashboard.repository.comment",
                "com.be01.be_01_01.dashboard.repository.user");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.format_sql","true");
        properties.put("hibernate.use_sql_comment","true");

        em.setJpaPropertyMap(properties);

        return em;

    }

    @Bean(name = "tmJpa")
    public PlatformTransactionManager transactionManager (DataSource dataSource){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean(dataSource).getObject());

        return transactionManager;

    }
}