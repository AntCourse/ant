package com.kzsrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Examination;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class ExaminationDao<E> extends BaseMybatisDao<Examination, String> {
	
	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.ExaminationMapper";
	}

	public List<Examination> getListByCour(String cid) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getListByCour", cid);
	}
}