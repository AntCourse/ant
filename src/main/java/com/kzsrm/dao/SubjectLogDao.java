package com.kzsrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.SubjectLog;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class SubjectLogDao<E> extends BaseMybatisDao<SubjectLog, String> {

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.SubjectLogMapper";
	}

	public List<SubjectLog> getByParam(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getByParam", param);
	}

}
