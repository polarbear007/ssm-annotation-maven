package cn.itcast.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@ComponentScan( basePackages= {"cn.itcast.ssm"},
				excludeFilters= {
					@Filter(type=FilterType.ANNOTATION, classes= {Controller.class, ControllerAdvice.class})
				}
			  )
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)  // 开启 aop 注解支持
@EnableTransactionManagement                    // 开启事务注解支持
public class SpringConfig {
	// 配置 dataSource
	@Bean
	public DataSource dataSource() throws Exception {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/spring_annotation?serverTimezone=Asia/Shanghai&useSSL=false");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	
	// 配置 mybatis 的sqlSessionFactory
	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(@Autowired DataSource dataSource) {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		// 设置dataSource
		factoryBean.setDataSource(dataSource);
		// 设置 mybatis 配置文件的位置
		factoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		
		// 设置插件类
		//factoryBean.setPlugins(plugins);
		return factoryBean;
	}
	
	// 配置事务管理器
	@Bean
	public DataSourceTransactionManager transactionManager(@Autowired DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	// 配置 mapper 代理开发的包扫描
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setBasePackage("cn.itcast.ssm.mapper");
		return configurer;
	}
}
