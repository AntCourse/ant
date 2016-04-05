package com.kzsrm.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kzsrm.model.Course;
import com.kzsrm.model.Keyword;
import com.kzsrm.model.Option;
import com.kzsrm.model.PointLog;
import com.kzsrm.model.Subject;
import com.kzsrm.model.Video;
import com.kzsrm.service.CourseService;
import com.kzsrm.service.KeywordService;
import com.kzsrm.service.OptionService;
import com.kzsrm.service.PointLogService;
import com.kzsrm.service.SubjectService;
import com.kzsrm.service.VideoService;
import com.kzsrm.utils.ApiCode;
import com.kzsrm.utils.CustomException;
import com.kzsrm.utils.MapResult;

@Controller
@RequestMapping("/courold")
public class CourseController {/*
	private static Logger logger = LoggerFactory.getLogger(Course.class);

	@Resource private CourseService courseService;
	@Resource private VideoService videoService;
	@Resource private SubjectService subjectService;
	@Resource private PointLogService pointLogService;
	@Resource private OptionService optionService;
	@Resource private KeywordService keywordService;

	*//**
	 * 课程列表-二级
	 * @param pid		课程id，返回本级及其子集信息
	 * @param type		预留参数
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getCourList_old")
	public Map<String, Object> getCourList_old(
			@RequestParam(required = true) String pid,
			@RequestParam(required = false) String type) {
		try{
			Map<String, Object> ret = MapResult.initMap();
			JSONObject course = new JSONObject();
			Course cour = courseService.getById(pid);
			if (cour != null){
				course.put("courId", cour.getId());
				course.put("courName", cour.getName());
				course.put("courProfile", cour.getProfile());
			}
			
			List<Course> courseList = courseService.getchildrenCour(pid, type);
			course.put("child", courseList);
			
			ret.put("result", course);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	*//**
	 * 课程列表-二级
	 * @param pid		课程id，返回本级及其子集信息
	 * @param type		预留参数
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getCourList")
	public Map<String, Object> getCourList(
			@RequestParam(required = true) String pid,
			@RequestParam(required = false) String type) {
		try{
			Map<String, Object> ret = MapResult.initMap();
			JSONObject course = new JSONObject();
			Course cour = courseService.getById(pid);
			if (cour != null){
				course.put("courId", cour.getId());
				course.put("courName", cour.getName());
				course.put("courProfile", cour.getProfile());
			}
			
			List<Course> courseList = courseService.getchildrenCour(pid, type);
			
			JSONArray children = new JSONArray();
			for (Course child : courseList){
				JSONObject ch = new JSONObject();
				ch.put("id", child.getId());
				ch.put("name", child.getName());
				ch.put("pid", child.getPid());
				ch.put("profile", child.getProfile());
				ch.put("type", child.getType());
				ch.put("address", child.getAddress());
				
				List<Course> subList = courseService.getchildrenCour(child.getId()+"", type);
				ch.put("subList", subList);
				
				children.add(ch);
			}
			
			course.put("child", children);
			
			ret.put("result", course);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	
	*//**
	 * 课程列表-三级
	 * @param pid		课程id，返回本级，子集及子集所包含的知识点
	 * @param type		预留参数
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getCourListSpe")
	public Map<String, Object> getCourListSpe(
			@RequestParam(required = true) String pid,
			@RequestParam(required = false) String type) {
		try{
			Map<String, Object> ret = MapResult.initMap();
			JSONObject course = new JSONObject();
			Course cour = courseService.getById(pid);
			if (cour != null){
				course.put("courId", cour.getId());
				course.put("courName", cour.getName());
				course.put("courProfile", cour.getProfile());
				course.put("playcount", cour.getPlaycount());
			}
			
			List<Course> courseList = courseService.getchildrenCour(pid, type);
			JSONArray children = new JSONArray();
			for (Course child : courseList){
				JSONObject ch = new JSONObject();
				ch.put("childId", child.getId());
				ch.put("childName", child.getName());
				
				List<Point> pointList = pointService.getPointByCour(child.getId() + "");
				for (Point poi : pointList) {
					Video video = videoService.getVideoByPoint(poi.getId()+"");
					poi.setVideoId(video.getId()+"");
					poi.setVideoAddr(video.getAddress());
				}
				ch.put("pointList", pointList);
				
				children.add(ch);
			}
			course.put("children", children);
			
			ret.put("result", course);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 知识点列表
	 * @param courseId		课程id
	 * @param userId		用户id，用于判断各知识点用户是否已学
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getPointList")
	public Map<String, Object> getPointList(
			@RequestParam(required = true) String courseId,
			@RequestParam(required = false) String userId) {
		try{
			if (StringUtils.isBlank(courseId))
				return MapResult.initMap(ApiCode.PARG_ERR, "课程id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Point> pointList = pointService.getPointByCour(courseId);
			if (StringUtils.isNotBlank(userId))
				for (Point point : pointList) {
					point.setIsLearn(pointLogService.checkIsLearn(point.getId()+"", userId));
				}
			ret.put("result", pointList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	*//**
	 * 首页推荐知识点
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getHomeVideoList")
	public Map<String, Object> getHomeVideoList() {
		try{
			
			Map<String, Object> ret = MapResult.initMap();
			List<Video> videoList = videoService.getHomeVideoList();
			ret.put("result", videoList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 知识点学习记录
	 * @param pointId		知识点id
	 * @param userId		用户id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/setPointLog")
	public Map<String, Object> setPointLog(
			@RequestParam(required = true) String pointId,
			@RequestParam(required = true) String userId) {
		try{
			if (StringUtils.isBlank(pointId))
				return MapResult.initMap(ApiCode.PARG_ERR, "知识点id为空");
			if (StringUtils.isBlank(userId))
				return MapResult.initMap(ApiCode.PARG_ERR, "用户id为空");
			
			PointLog pointLog = new PointLog();
			pointLog.setCreatetime(new Date());
			pointLog.setPid(pointId);
			pointLog.setUserid(userId);
			pointLogService.save(pointLog);
			
			Map<String, Object> ret = MapResult.initMap();
			ret.put("result", "保存成功");
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 获取知识点对应的视频
	 * @param pointId		知识点id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getVideoByPoi")
	public Map<String, Object> getVideoByPoi(
			@RequestParam(required = true) String pointId) {
		try{
			if (StringUtils.isBlank(pointId))
				return MapResult.initMap(ApiCode.PARG_ERR, "知识点id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			Video video = videoService.getVideoByPoint(pointId);
			ret.put("result", video);
			ret.put("pointId", pointId);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	*//**
	 * 获取推荐的搜索关键词
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getKeywordList")
	public Map<String, Object> getKeywordList() {
		try{
			Map<String, Object> ret = MapResult.initMap();
			List<Keyword> keywordList = keywordService.getKeywordList();
			ret.put("result", keywordList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	*//**
	 * 根据标签查询对应的视频（视频检索）
	 * @param pointId		知识点id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getVideoByKeyWord")
	public Map<String, Object> getVideoByKeyWord(
			@RequestParam(required = true) String keyword) {
		try{
			if (StringUtils.isBlank(keyword))
				return MapResult.initMap(ApiCode.PARG_ERR, "参数为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Video> video = videoService.getVideoByTag(keyword);
			ret.put("result", video);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	*//**
	 * 获取知识点详细信息
	 * @param pointId		知识点id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getPointDetail")
	public Map<String, Object> getPointDetail(
			@RequestParam(required = true) String pointId) {
		try{
			if (StringUtils.isBlank(pointId))
				return MapResult.initMap(ApiCode.PARG_ERR, "知识点id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			Point point = pointService.getById(pointId);
			ret.put("result", point);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	*//**
	 * 更新课程（视频文件夹）信息
	 * 每调用一次，课程点击数+1，同时更新时长总计
	 * @param videoId		视频id
	 * @param timeSpan		时长，秒
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/updateCourseInfo")
	public Map<String, Object> updateCourseInfo(
			@RequestParam(required = true) String courseId) {
		try{
			if (StringUtils.isBlank(courseId))
				return MapResult.initMap(ApiCode.PARG_ERR, "课程id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			courseService.updateCourseInfo(courseId);
			return ret;
		} catch (CustomException e) {
			return MapResult.initMap(-1, e.getMessage());
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	*//**
	 * 更新视频信息
	 * 每调用一次，视频点击数+1，同时更新时长总计
	 * @param videoId		视频id
	 * @param timeSpan		时长，秒
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/updVideoInfo")
	public Map<String, Object> updateVideoInfo(
			@RequestParam(required = true) String videoId,
			@RequestParam(required = false) String timeSpan) {
		try{
			if (StringUtils.isBlank(videoId))
				return MapResult.initMap(ApiCode.PARG_ERR, "视频id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			videoService.updateVideoInfo(videoId, timeSpan);
			return ret;
		} catch (CustomException e) {
			return MapResult.initMap(-1, e.getMessage());
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 获取视频对应的测试题
	 * @param videoId		视频id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getSubList")
	public Map<String, Object> getSubjectList(
			@RequestParam(required = true) String videoId) {
		try{
			if (StringUtils.isBlank(videoId))
				return MapResult.initMap(ApiCode.PARG_ERR, "视频id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Subject> subList = subjectService.getSubjectByVideo(videoId);
			ret.put("result", subList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 提交测试题的答案
	 * @param userId		用户id，用于记录做题日志
	 * @param answer		答案，json格式[{"no":"序号","optId":"选项id","timeSpan":"秒"},{},...]
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/checkAnswer")
	public Map<String, Object> checkAnswer(
			@RequestParam(required = false) String userId,
			@RequestParam(required = true) String answer) {
		try{
			if (StringUtils.isBlank(answer))
				return MapResult.initMap(ApiCode.PARG_ERR, "答案为空");
			
			Map<String, Object> ret = MapResult.initMap();
			JSONArray subList = subjectService.checkAnswer(userId, answer);
			ret.put("result", subList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 获取测试题的相关视频
	 * @param subjectId		试题id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getVideoBySub")
	public Map<String, Object> getVideoBySub(
			@RequestParam(required = true) String subjectId) {
		try{
			if (StringUtils.isBlank(subjectId))
				return MapResult.initMap(ApiCode.PARG_ERR, "试题id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Video> videoList = videoService.getVideoBySubject(subjectId);
			ret.put("result", videoList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 获取推荐视频
	 * @param subjectIds		错误试题id，多个用逗号分隔
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getRecommendVideo")
	public Map<String, Object> getRecommendVideo(
			@RequestParam(required = true) String subjectIds) {
		try{
			if (StringUtils.isBlank(subjectIds))
				return MapResult.initMap(ApiCode.PARG_ERR, "试题id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			Video video = videoService.getRecommendVideo(subjectIds);
			ret.put("result", video);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 获取试题答案
	 * @param subjectId			试题id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getSubjectAnswer")
	public Map<String, Object> getSubjectAnswer(
			@RequestParam(required = true) String subjectId) {
		try{
			if (StringUtils.isBlank(subjectId))
				return MapResult.initMap(ApiCode.PARG_ERR, "试题id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			Option option = optionService.getSubjectAnswer(subjectId);
			ret.put("result", option);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	*//**
	 * 获取试题提示
	 * @param subjectId			试题id
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "/getSubjectHint")
	public Map<String, Object> getSubjectHint(
			@RequestParam(required = true) String subjectId) {
		try{
			if (StringUtils.isBlank(subjectId))
				return MapResult.initMap(ApiCode.PARG_ERR, "试题id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			Subject subject = subjectService.getById(subjectId);
			ret.put("result", subject.getHint());
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
*/}
