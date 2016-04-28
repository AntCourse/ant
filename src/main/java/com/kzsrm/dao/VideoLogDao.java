package com.kzsrm.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.VideoLog;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class VideoLogDao<E> extends BaseMybatisDao<VideoLog, String> {

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.VideoLogMapper";
	}

	public VideoLog getVideoLog(Map<String, Object> map) {
		return this.getSqlSession().selectOne(this.getMybatisMapperNamesapce() + ".getVideoLog", map);
	}

}
