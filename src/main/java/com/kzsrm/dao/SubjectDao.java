package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Subject;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class SubjectDao<E> extends BaseMybatisDao<Subject, Integer> {
	private static final String loc = "com.kzsrm.model.SubjectMapper";

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.SubjectMapper";
	}

}
