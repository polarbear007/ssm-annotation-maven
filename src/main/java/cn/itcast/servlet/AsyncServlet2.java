package cn.itcast.servlet;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 先在注解上面添加对异步处理的支持
@WebServlet(value="/asyncTest2", asyncSupported=true)
public class AsyncServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		AsyncContext asyncContext = request.startAsync();
		// 设置异步处理的等待时间，如果超过这个时间，就会抛异常
		asyncContext.setTimeout(5000);
		// 给这个异步处理上下文添加一个监听器对象
		asyncContext.addListener(new MyAsyncListener());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		// 开始异步处理
		asyncContext.start(new Runnable() {
			@Override
			public void run() {
				// 如果我们把这个休眠的时长改成超过 5000 毫秒，就会超时
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// 处理成功以后，给页面打印一下成功信息
				try {
					asyncContext.getResponse().getWriter().write("success");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// 选择性地在这里抛个异常
				//System.out.println(10/0);
				
				// 处理完毕以后，调用 complete() 方法结束 
				asyncContext.complete();
			}
		});	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
