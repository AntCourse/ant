package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.SubjectLog;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class SubjectLogDao<E> extends BaseMybatisDao<SubjectLog, String> {

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.SubjectLogMapper";
	}

}
