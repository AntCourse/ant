package com.kzsrm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.CourseDao;
import com.kzsrm.model.Course;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.CourseService;
import com.kzsrm.utils.CustomException;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceMybatisImpl<Course, String> implements CourseService {
	@Resource
	private CourseDao<?> courseDao;

	@Override
	protected EntityDao<Course, String> getEntityDao() {
		return courseDao;
	}

	/**
	 * 查询pid下的所有子项
	 * @param pid
	 * @param type
	 * @return
	 */
	@Override
	public List<Course> getchildrenCour(String pid, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("type", type);
		return courseDao.getchildrenCour(map);
	}
	
	/**
	 * 查询课程
	 * @param pid
	 * @return
	 */
	@Override
	public Course getCourseById(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return courseDao.getCourseById(map);
	}
	
	/**
	 * 更新课程信息
	 * @param videoId
	 * @throws CustomException 
	 */
	@Override
	public void updateCourseInfo(String courseId) throws CustomException {

		Course course = courseDao.getById(courseId);
		if (course == null)
			throw new CustomException("课程不存在");
		
		course.setPlaycount(course.getPlaycount() + 1);
		courseDao.update(course);
	}

}
