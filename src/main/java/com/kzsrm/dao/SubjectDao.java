package com.kzsrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Subject;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class SubjectDao<E> extends BaseMybatisDao<Subject, String> {

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.SubjectMapper";
	}

	public List<Subject> getSubjectByVideo(String videoId) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getSubjectByVideo", videoId);
	}

}
