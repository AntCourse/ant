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

	public List<Subject> getSubjectByPoint(String pointId) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getSubjectByPoint", pointId);
	}

	public Integer getSubNumByPoint(String pointId) {
		return this.getSqlSession().selectOne(this.getMybatisMapperNamesapce() + ".getSubNumByPoint", pointId);
	}

	public List<Subject> getSubjectByExam(String examId) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getSubjectByExam", examId);
	}

}
