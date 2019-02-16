package cn.itcast.servlet;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 先在注解上面添加对异步处理的支持
@WebServlet(value="/asyncTest", asyncSupported=true)
public class AsyncServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 打印一下，主线程接受请求的时间
		System.out.println("主线程: "+ Thread.currentThread().getName() + ",接收请求的时间：" + System.currentTimeMillis());
		// 然后在方法中，显式地开启异步处理
		AsyncContext asyncContext = request.startAsync();
		// 调用start() 方法，开启新的线程去处理业务请求
		asyncContext.start(new Runnable() {
			// 处理业务请求的具体实现
			@Override
			public void run() {
				System.out.println("处理线程: "+ Thread.currentThread().getName() + ",开始处理的时间：" + System.currentTimeMillis());
				// 我们仅仅是模拟一下处理的过程，这里直接 sleep 3 秒
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 主要的业务处理完以后，我们需要调用  asyncContext.complete() 方法告诉serlvet 你的处理线程已经处理完毕，等待返回处理结果
				asyncContext.complete();
				// 我们处理结束以后，需要拿到 response 对象，给浏览器返回一些提示信息，这个时候我们需要从异步的上下文中获取 response 对象
				try {
					asyncContext.getResponse().getWriter().write("异步处理成功！！！" + UUID.randomUUID());
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("处理线程: "+ Thread.currentThread().getName() + ",处理结束的时间：" + System.currentTimeMillis());
				
			}
		});
		System.out.println("主线程: "+ Thread.currentThread().getName() + ",结束请求的时间：" + System.currentTimeMillis());
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
