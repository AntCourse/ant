package com.kzsrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Video;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class VideoDao<E> extends BaseMybatisDao<Video, String> {

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.VideoMapper";
	}

	public Video getVideoByPoint(String pointId) {
		return this.getSqlSession().selectOne(this.getMybatisMapperNamesapce() + ".getVideoByPoint", pointId);
	}

	public List<Video> getVideoBySubject(String subjectId) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getVideoBySubject", subjectId);
	}

}
