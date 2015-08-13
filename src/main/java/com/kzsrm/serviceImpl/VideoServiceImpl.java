package com.kzsrm.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.VideoDao;
import com.kzsrm.model.Video;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.VideoService;
import com.kzsrm.utils.ComUtils;
import com.kzsrm.utils.CustomException;

@Service
@Transactional
public class VideoServiceImpl extends BaseServiceMybatisImpl<Video, Integer> implements VideoService {
	@Resource
	private VideoDao<?> videoDao;

	@Override
	protected EntityDao<Video, String> getEntityDao() {
		return videoDao;
	}

	/**
	 * 获取知识点对应的视频
	 * @param pointId
	 * @return
	 */
	@Override
	public Video getVideoByPoint(String pointId) {
		return videoDao.getVideoByPoint(pointId);
	}
	/**
	 * 更新视频信息
	 * @param videoId
	 * @param timeSpan
	 * @throws CustomException 
	 */
	@Override
	public void updateVideoInfo(String videoId, String timeSpan) throws CustomException {
		int _timeSpan = ComUtils.parseInt(timeSpan);
		
		Video video = videoDao.getById(videoId);
		if (video == null)
			throw new CustomException("视频不存在");
		
		video.setPlayCount(video.getPlayCount() + 1);
		video.setTimeSpan(video.getTimeSpan() + _timeSpan);
		videoDao.update(video);
	}
	/**
	 * 获取试题相关的视频
	 * @param subjectId
	 * @return
	 */
	@Override
	public List<Video> getVideoBySubject(String subjectId) {
		return videoDao.getVideoBySubject(subjectId);
	}

}
