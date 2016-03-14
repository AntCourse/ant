package com.kzsrm.service;

import java.util.List;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Course;
import com.kzsrm.model.Video;
import com.kzsrm.utils.ComUtils;
import com.kzsrm.utils.CustomException;

public interface CourseService  extends BaseServiceMybatis<Course, String> {

	/**
	 * 查询pid下的所有子项
	 * @param pid
	 * @param type
	 * @return
	 */
	List<Course> getchildrenCour(String pid, String type);
	
	/**
	 * 查询课程
	 * @param pid
	 * @return
	 */
	Course getCourseById(String id);
	
	/**
	 * 更新课程信息
	 * @param videoId
	 * @param timeSpan
	 * @throws CustomException 
	 */
    void updateCourseInfo(String courseId) throws CustomException;

}
