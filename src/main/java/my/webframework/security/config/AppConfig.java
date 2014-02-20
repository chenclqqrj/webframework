package my.webframework.security.config;

import java.util.Properties;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(value = { "my.webframework.security" })
@PropertySource("classpath:config.properties")
@EnableJpaRepositories(basePackages = { "my.webframework.security.reponsitory" })
@EnableTransactionManagement
public class AppConfig {

	@Inject
	Environment env;

	@Bean
	public DataSource dataSource() {
		ProxoolDataSource pds = new ProxoolDataSource("mysql");
		pds.setDriver(env.getProperty("jdbc.driverclass"));
		pds.setDriverUrl(env.getProperty("jdbc.url"));
		pds.setUser(env.getProperty("jdbc.username"));
		pds.setPassword(env.getProperty("jdbc.password"));
		pds.setMaximumConnectionCount(8);
		pds.setMinimumConnectionCount(4);
		pds.setPrototypeCount(2);
		pds.setHouseKeepingSleepTime(90000);
		pds.setSimultaneousBuildThrottle(2);
		pds.setMaximumActiveTime(360000);
		pds.setMaximumConnectionLifetime(14400000);
		return pds;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	@Autowired
	// 此处也可以不用注入，而使用方法调用
	public EntityManagerFactory entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true); // 牛逼的操作
		vendorAdapter.setShowSql(false);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		vendorAdapter.setDatabase(Database.MYSQL);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("my.webframework.security.domain", "my.webframework.share"); // 不正确，会出现"Not a managed bean"错误
		// means not a type the JPA
		// provider is aware of.
		factory.setDataSource(dataSource);

		Properties properties = new Properties();
		properties.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.generate_statistics", "true");

		factory.setJpaProperties(properties);

		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		JpaDialect jpaDialect = new HibernateJpaDialect();
		txManager.setEntityManagerFactory(entityManagerFactory);
		txManager.setJpaDialect(jpaDialect);
		return txManager;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
