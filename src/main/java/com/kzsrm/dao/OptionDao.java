package com.kzsrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Option;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class OptionDao<E> extends BaseMybatisDao<Option, String> {
	
	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.OptionMapper";
	}

	public List<Option> getOptionBySubject(Integer subjectId) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getOptionBySubject", subjectId);
	}

}
