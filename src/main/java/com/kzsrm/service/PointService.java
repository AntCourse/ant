package com.kzsrm.service;

import java.util.List;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Point;

public interface PointService  extends BaseServiceMybatis<Point, Integer> {

	/**
	 * 查询课程中的知识点
	 * @param pid
	 * @param type
	 * @return
	 */
	List<Point> getPointByCour(String courseId);

}
