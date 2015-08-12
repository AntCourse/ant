package com.kzsrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Point;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class PointDao<E> extends BaseMybatisDao<Point, String> {

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.PointMapper";
	}

	public List<Point> getPointByCour(String courseId) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getPointByCour", courseId);
	}
}
