package com.kzsrm.service;

import java.util.List;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Course;
import com.kzsrm.utils.CustomException;

import net.sf.json.JSONObject;

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
    /**
     * 钻取多级课程
     * @param pid
     * @param userid 
     * @param type 
     * @return
     */
	JSONObject getMultilevelCour(Course course, String userid, String type);
	/**
	 * 刷新知识点下的题目总数
	 * @param pid 
	 * @param type 
	 */
	int refreshSubAllNum(String pid, String type);

}
