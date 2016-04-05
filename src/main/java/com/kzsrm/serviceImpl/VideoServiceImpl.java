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
public class VideoServiceImpl extends BaseServiceMybatisImpl<Video, String> implements VideoService {
	@Resource
	private VideoDao<?> videoDao;

	@Override
	protected EntityDao<Video, String> getEntityDao() {
		return videoDao;
	}
	
	@Override
	public Video getVideoById(Integer id){
		return videoDao.getById(id);
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
	@Override
	public List<Video> getVideoListByPoint(String pointId) {
		return videoDao.getVideoListByPoint(pointId);
	}
	
	/**
	 * 根据标签获取视频（模糊检索）
	 * @param pointId
	 * @return
	 */
	@Override
	public List<Video> getVideoByTag(String keyword) {
		return videoDao.getVideoByTag(keyword);
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
	/**
	 * 获取推荐视频
	 * @param subjectIds
	 * @return
	 */
	@Override
	public Video getRecommendVideo(String subjectIds) {return null;/*
		Map<Integer, Integer> repeat = new HashMap<Integer, Integer>();
		Integer maxCount = 0, maxPoint = null;
		
		for (String sid : subjectIds.split(",")) {
			List<Point> plist = pointDao.getPoisBySub(sid);
			for (Point point : plist) {
				Integer pid = point.getId();
				if (repeat.containsKey(pid)) {
					Integer count = repeat.get(pid) + 1;
					repeat.put(pid, count);
					if (count > maxCount){
						maxCount = count;
						maxPoint = pid;
					}
				} else {
					repeat.put(pid, 1);
					if (maxCount == 0) {
						maxCount = 1;
						maxPoint = pid;
					}
				}
			}
		}
		
		return videoDao.getVideoByPoint(maxPoint+"");
	*/}
	
	/**
	 * 获取首页轮播中展示的视频
	 * @param subjectId
	 * @return
	 */
	@Override
	public List<Video> getHomeVideoList(){
		return videoDao.getBannerVideo();
	}

}
