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

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceMybatisImpl<Course, Integer> implements CourseService {
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

}
