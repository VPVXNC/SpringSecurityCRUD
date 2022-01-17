package myapp.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan("myapp")
@PropertySource("classpath:db.properties")
public class AppConfig {
    @Resource
    private Environment environment;


    @Bean
    public LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan(environment.getRequiredProperty("db.entity.package"));
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaProperties(getHibernateProperties());

        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();

        basicDataSource.setUrl(environment.getRequiredProperty("db.url"));
        basicDataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
        basicDataSource.setUsername(environment.getRequiredProperty("db.username"));
        basicDataSource.setPassword(environment.getRequiredProperty("db.password"));

        return basicDataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}
