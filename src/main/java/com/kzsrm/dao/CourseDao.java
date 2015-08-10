package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Course;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class CourseDao<E> extends BaseMybatisDao<Course, Integer> {
	private static final String loc = "com.kzsrm.model.CourseMapper";

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.CourseMapper";
	}

}
