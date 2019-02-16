package cn.itcast.initializer;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import cn.itcast.config.SpringConfig;
import cn.itcast.config.SpringmvcConfig;

public class MyContextLoaderInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	// spring ioc 容器的配置类
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{SpringConfig.class};
	}

	// springmvc 容器的配置类
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {SpringmvcConfig.class};
	}

	// springmvc 的 dispatcherServlet 的url-pattern
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
}
