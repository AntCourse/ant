package com.kzsrm.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.CourseDao;
import com.kzsrm.model.Course;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.CourseService;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceMybatisImpl<Course, Integer> implements CourseService {
	@Resource
	private CourseDao<?> courseDao;

	@Override
	protected EntityDao<Course, Integer> getEntityDao() {
		return courseDao;
	}

}
