package cn.itcast.ssm.mapper;

import org.apache.ibatis.annotations.Param;

import cn.itcast.ssm.entity.Student;

public interface StudentMapper {
	public void insertStudent(@Param("stu") Student stu);

	public Student getStudentById(Integer sno);
}
