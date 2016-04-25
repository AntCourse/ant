package com.kzsrm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.SubjectExamLog;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class SubjectExamLogDao<E> extends BaseMybatisDao<SubjectExamLog, String> {

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.SubjectExamLogMapper";
	}

	public List<SubjectExamLog> getByParam(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getByParam", param);
	}

}
