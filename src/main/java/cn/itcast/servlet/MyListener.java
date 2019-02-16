package cn.itcast.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

// servlet 原生api 提供了多种监听器，我们这里只拿 ServletContextListener 演示
// @WebListener
public class MyListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("MyListener: serlvet容器初始化....");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("MyListener: servlet 容器销毁...");
	}
}
