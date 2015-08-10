package com.kzsrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Course;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class CourseDao<E> extends BaseMybatisDao<Course, Integer> {
	
	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.CourseMapper";
	}

	public List<Course> getchildrenCour(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getchildrenCour", param);
	}

}
