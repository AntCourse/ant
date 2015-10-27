package com.kzsrm.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.PointDao;
import com.kzsrm.model.Point;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.PointService;

@Service
@Transactional
public class PointServiceImpl extends BaseServiceMybatisImpl<Point, String> implements PointService {
	@Resource
	private PointDao<?> pointDao;

	@Override
	protected EntityDao<Point, String> getEntityDao() {
		return pointDao;
	}

	/**
	 * 查询课程中的知识点
	 * @param pid
	 * @param type
	 * @return
	 */
	@Override
	public List<Point> getPointByCour(String courseId) {
		return pointDao.getPointByCour(courseId);
	}

}
