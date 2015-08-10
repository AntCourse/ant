package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Point;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class PointDao<E> extends BaseMybatisDao<Point, Integer> {
	private static final String loc = "com.kzsrm.model.PointMapper";

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.PointMapper";
	}

}
