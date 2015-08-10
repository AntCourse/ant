package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Tag;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class TagDao<E> extends BaseMybatisDao<Tag, Integer> {
	private static final String loc = "com.kzsrm.model.TagMapper";

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.TagMapper";
	}

}
