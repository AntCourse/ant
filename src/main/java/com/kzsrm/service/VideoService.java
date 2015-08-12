package com.kzsrm.service;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Video;
import com.kzsrm.utils.CustomException;

public interface VideoService  extends BaseServiceMybatis<Video, Integer> {

	/**
	 * 获取知识点对应的视频
	 * @param pointId
	 * @return
	 */
	Video getVideoByPoint(String pointId);
	/**
	 * 更新视频信息
	 * @param videoId
	 * @param timeSpan
	 * @throws CustomException 
	 */
	void updateVideoInfo(String videoId, String timeSpan) throws CustomException;

}
