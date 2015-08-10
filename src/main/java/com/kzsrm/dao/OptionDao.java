package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Option;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class OptionDao<E> extends BaseMybatisDao<Option, Integer> {
	private static final String loc = "com.kzsrm.model.OptionMapper";

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.OptionMapper";
	}

}
