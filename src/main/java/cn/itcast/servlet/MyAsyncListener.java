package cn.itcast.servlet;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

public class MyAsyncListener implements AsyncListener {

	@Override
	public void onComplete(AsyncEvent event) throws IOException {
		System.out.println("异步线程处理完毕！！");
	}

	@Override
	public void onTimeout(AsyncEvent event) throws IOException {
		System.out.println("异步线程处理超时！！");
	}

	@Override
	public void onError(AsyncEvent event) throws IOException {
		System.out.println("异步线程处理异常！！");
	}

	@Override
	public void onStartAsync(AsyncEvent event) throws IOException {
		System.out.println("异步线程开启！！");
	}

}
