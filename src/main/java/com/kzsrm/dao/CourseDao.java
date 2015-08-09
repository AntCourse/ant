package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Course;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class CourseDao<E> extends BaseMybatisDao<Course, Integer> {
	private static final String MAPPER = "com.kzsrm.model.CourseMapper";

	@Override
	public String getMybatisMapperNamesapce() {
		return MAPPER;
	}

}
