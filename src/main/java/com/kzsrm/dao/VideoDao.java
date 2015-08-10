package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Video;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class VideoDao<E> extends BaseMybatisDao<Video, Integer> {
	private static final String loc = "com.kzsrm.model.VideoMapper";

	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.VideoMapper";
	}

}
