package cn.itcast.ssm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.ssm.entity.Student;
import cn.itcast.ssm.mapper.StudentMapper;


@Service
public class StudentService {
	@Autowired
	private StudentMapper studentMapper;
	
	@Transactional
	public void insertStudent(Student stu) {
		studentMapper.insertStudent(stu);
	}

	public Student getStudentById(Integer sno) {
		return studentMapper.getStudentById(sno);
	}
	
	
}
