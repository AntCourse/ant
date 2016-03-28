package com.kzsrm.service;

import java.util.List;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Video;
import com.kzsrm.utils.CustomException;

public interface VideoService  extends BaseServiceMybatis<Video, String> {

	/**
	 * 获取视频信息
	 * @param Id
	 * @return
	 */
	Video getVideoById(Integer id);
	/**
	 * 获取知识点对应的视频
	 * @param pointId
	 * @return
	 */
	Video getVideoByPoint(String pointId);
	List<Video> getVideoListByPoint(String pointId);
	/**
	 * 根据标签获取视频（模糊检索）
	 * @param pointId
	 * @return
	 */
	List<Video> getVideoByTag(String keyword);
	/**
	 * 更新视频信息
	 * @param videoId
	 * @param timeSpan
	 * @throws CustomException 
	 */
	void updateVideoInfo(String videoId, String timeSpan) throws CustomException;
	/**
	 * 获取试题相关的视频
	 * @param subjectId
	 * @return
	 */
	List<Video> getVideoBySubject(String subjectId);
	/**
	 * 获取推荐视频
	 * @param subjectIds
	 * @return
	 */
	Video getRecommendVideo(String subjectIds);

	/**
	 * 获取首页轮播中展示的视频
	 * @param subjectId
	 * @return
	 */
	List<Video> getHomeVideoList();
}
