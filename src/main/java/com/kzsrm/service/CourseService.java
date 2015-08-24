package com.kzsrm.service;

import java.util.List;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Course;

public interface CourseService  extends BaseServiceMybatis<Course, Integer> {

	/**
	 * 查询pid下的所有子项
	 * @param pid
	 * @param type
	 * @return
	 */
	List<Course> getchildrenCour(String pid, String type);

}
