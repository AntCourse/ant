package com.kzsrm.dao;

import java.util.List;
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
	
	public List<VideoLog> getVideoList(Map<String, Object> map) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getVideoList", map);
	}

}
