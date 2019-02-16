package cn.itcast.initializer;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import cn.itcast.servlet.HelloServlet;
import cn.itcast.servlet.MyFilter;
import cn.itcast.servlet.MyListener;

//  这个类的作用有点像 ServletContextListener, 就是在ServletContext 初始化的时候
//  执行这个类重写的 onStartup 方法，我们可以在这个方法里面添加需要加载的 servlet/ listener / filter

//  【注意】 这个类并不是我们实现了这个接口以后， servlet 容器在启动的时候就会自动加载 
//         servlet 3.0 版本在启动 servlet 容器的时候，会自动去加载每个jar 包下面的 
//         META-INF/services/javax.servlet.ServletContainerInitializer 文件里面的内容
//         我们需要 把我们实现 ServletContainerInitializer 接口的类的全路径名写在这个文件中让 servlet 容器去加载
public class MyServletContainerInitializer implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		// 添加filter 组件 
		FilterRegistration.Dynamic myFilter = ctx.addFilter("myFilter", MyFilter.class);
		// filter 还得指定url-pattern , 过滤哪些 请求
		// 前面两个参数我们就固定那样写就好了，后面的参数其实也算是固定的，拦截所有的请求
		myFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
		
		// 添加 servlet 组件
		ServletRegistration.Dynamic helloServlet = ctx.addServlet("helloServlet", HelloServlet.class);
		// 设置 servlet 的 url-pattern 
		helloServlet.addMapping("/hello");
		
		// 添加 listener 组件, listener 不需要指定什么 url-pattern
		ctx.addListener(MyListener.class);
		
	}

}
