package com.kzsrm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kzsrm.model.Course;
import com.kzsrm.model.Point;
import com.kzsrm.model.Subject;
import com.kzsrm.model.User;
import com.kzsrm.model.Video;
import com.kzsrm.service.CourseService;
import com.kzsrm.service.PointService;
import com.kzsrm.service.SubjectService;
import com.kzsrm.service.VideoService;
import com.kzsrm.utils.ApiCode;
import com.kzsrm.utils.CustomException;
import com.kzsrm.utils.MapResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/cour")
public class CourseController {
	private static Logger logger = LoggerFactory.getLogger(User.class);

	@Resource private CourseService courseService;
	@Resource private PointService pointService;
	@Resource private VideoService videoService;
	@Resource private SubjectService subjectService;

	/**
	 * 课程列表-二级
	 * @param pid		父级id，为空时查询顶层结点
	 * @param type		预留参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCourList")
	public Map<String, Object> getCourList(
			@RequestParam(required = false) String pid,
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
	/**
	 * 课程列表-三级
	 * @param pid		父级id，不能为空
	 * @param type		预留参数
	 * @return
	 */
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
			}
			
			List<Course> courseList = courseService.getchildrenCour(pid, type);
			JSONArray children = new JSONArray();
			for (Course child : courseList){
				JSONObject ch = new JSONObject();
				ch.put("childId", child.getId());
				ch.put("childName", child.getName());
				
				List<Point> pointList = pointService.getPointByCour(child.getId() + "");
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
	/**
	 * 知识点列表
	 * @param courseId		课程id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPointList")
	public Map<String, Object> getPointList(
			@RequestParam(required = true) String courseId) {
		try{
			if (StringUtils.isBlank(courseId))
				return MapResult.initMap(ApiCode.PARG_ERR, "课程id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Point> pointList = pointService.getPointByCour(courseId);
			ret.put("result", pointList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	/**
	 * 获取知识点对应的视频
	 * @param pointId		知识点id
	 * @return
	 */
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
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	/**
	 * 获取知识点详细信息
	 * @param pointId		知识点id
	 * @return
	 */
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
	/**
	 * 更新视频信息
	 * 每调用一次，视频点击数+1，同时更新时长总计
	 * @param videoId		视频id
	 * @param timeSpan		时长，秒
	 * @return
	 */
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
	/**
	 * 获取视频对应的测试题
	 * @param videoId		视频id
	 * @return
	 */
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
	/**
	 * 提交测试题的答案
	 * @param answer		答案，json格式[{"no":"序号","optId":"选项id","timeSpan":"秒"},{},...]
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkAnswer")
	public Map<String, Object> checkAnswer(
			@RequestParam(required = true) String answer) {
		try{
			if (StringUtils.isBlank(answer))
				return MapResult.initMap(ApiCode.PARG_ERR, "答案为空");
			
			Map<String, Object> ret = MapResult.initMap();
			JSONArray subList = subjectService.checkAnswer(answer);
			ret.put("result", subList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	/**
	 * 获取测试题的相关视频
	 * @param subjectId		试题id
	 * @return
	 */
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
	/**
	 * 获取推荐视频
	 * @param subjectIds		错误试题id，多个用逗号分隔
	 * @return
	 */
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
}
