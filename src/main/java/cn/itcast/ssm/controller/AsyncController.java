package cn.itcast.ssm.controller;

import java.util.Vector;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import cn.itcast.ssm.entity.Student;
import cn.itcast.ssm.service.StudentService;

@Controller
@RequestMapping("/async")
public class AsyncController {
	@Autowired
	private StudentService studentService;
	
	// 声明一个线程安全的集合来保存  DeferredResult 对象
	private Vector<DeferredResult<String>> vector = new Vector<>();
	
	@RequestMapping("/testCallable")
	public Callable<String> testCallable(){
		return new Callable<String>() {

			@Override
			public String call() throws Exception {
				// 我们没有什么任务可以处理的，这里就直接休眠 3 秒
				Thread.sleep(3000);
				return "success";
			}
		};
	}
	
	@ResponseBody
	@RequestMapping("/testCallable2")
	public Callable<Student> testCallable2(){
		return new Callable<Student>() {

			@Override
			public Student call() throws Exception {
				// 我们这里同样是休眠个 3 秒
				Thread.sleep(3000);
				return studentService.getStudentById(1);
			}
		};
	}
	
	@RequestMapping("/testDeferredResult")
	public DeferredResult<String> testDeferedResult(){
		// 创建 DeferredResult 对象
		DeferredResult<String> deferredResult = new DeferredResult<String>();
		// 先把这个对象保存进一个线程安全的集合里面
		vector.add(deferredResult);
		// 虽然我们还没有真正处理，但是可以先把这个 DeferredResult 对象返回
		return deferredResult;
	}
	
	// 这里我们使用另一个handler 方法来异步处理 DeferredResult 
	// 真实的开发中肯定不会这样去弄的，这里只是为了演示方便
	@RequestMapping("/handleDeferredResult")
	public String handleDeferredResult(Model model) throws Exception {
		if(vector.isEmpty()) {
			model.addAttribute("message", "没有任务可以处理！");
			return "error";
		}else {
			// 获取并删除集合中的一个元素
			DeferredResult<String> deferredResult = vector.remove(0);
			// 然后我们随便干点什么，比如休眠个1秒
			Thread.sleep(1000);
			// 最后根据处理结果，设置要返回的结果
			// 这里的结果是一个对象，就是我们声明 DeferredResult 的泛型
			// 可以是 string 类型，其实也可以是Object 类型（@ResponseBody）
			deferredResult.setResult("success");
			
			// 处理请求页面要跳转的页面
			return "success";
		}
	}
}
