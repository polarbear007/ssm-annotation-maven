package cn.itcast.ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.ssm.entity.Student;
import cn.itcast.ssm.service.StudentService;

@Controller
@RequestMapping("/stu")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@RequestMapping("/addStudent")
	public String addStudent() {
		// 因为懒得再去写一个表单，所以这里直接把 数据写死了
		Student stu = new Student();
		stu.setName("小明");
		stu.setAge(12);
		
		studentService.insertStudent(stu);
		
		return "success";
	}
}
