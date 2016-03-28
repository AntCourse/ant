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
import com.kzsrm.model.Option;
import com.kzsrm.model.Point;
import com.kzsrm.model.Subject;
import com.kzsrm.model.Video;
import com.kzsrm.service.CourseService;
import com.kzsrm.service.OptionService;
import com.kzsrm.service.PointLogService;
import com.kzsrm.service.PointService;
import com.kzsrm.service.SubjectService;
import com.kzsrm.service.VideoService;
import com.kzsrm.utils.ApiCode;
import com.kzsrm.utils.ComUtils;
import com.kzsrm.utils.MapResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/cour")
public class CourseNewController {
	private static Logger logger = LoggerFactory.getLogger(Course.class);
	JsonConfig courCf = ComUtils.jsonConfig(new String[]{"id","reDate"});

	@Resource private CourseService courseService;
	@Resource private PointService pointService;
	@Resource private VideoService videoService;
	@Resource private SubjectService subjectService;
	@Resource private PointLogService pointLogService;
	@Resource private OptionService optionService;

	/**
	 * 课程列表-三层
	 * @param pid		课程id，返回本级及其子集信息
	 * @param type		预留参数
	 * @return
	 */
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
	/**
	 * 知识点列表
	 * @param courseId		课程id
	 * @param userId		用户id，用于判断各知识点用户是否已学
	 * @return
	 */
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
			for (Point point : pointList) {
				point.setSubNum(subjectService.getSubNumByPoint(point.getId()+""));
				if (StringUtils.isNotBlank(userId))
					point.setAccuracy(pointLogService.getAccuracy(point.getId()+"", userId));
			}
			ret.put("result", pointList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	/**
	 * 获取知识点对应的测试题
	 * @param pointId		知识点id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPoiSubList")
	public Map<String, Object> getPoiSubjectList(
			@RequestParam(required = true) String pointId) {
		try{
			if (StringUtils.isBlank(pointId))
				return MapResult.initMap(ApiCode.PARG_ERR, "知识点id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Subject> subList = subjectService.getSubjectByPoint(pointId);
			ret.put("result", subList);
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
			List<Video> videoList = videoService.getVideoListByPoint(pointId);
			ret.put("result", videoList);
			ret.put("pointId", pointId);
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
	 * 提交测试题的答案
	 * @param userId		用户id，用于记录做题日志
	 * @param answer		答案，json格式[{"no":"序号","optId":"选项id","timeSpan":"秒"},{},...]
	 * @return
	 */
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
	/**
	 * 获取试题答案
	 * @param subjectId			试题id
	 * @return
	 */
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
	/**
	 * 获取试题提示
	 * @param subjectId			试题id
	 * @return
	 */
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
}
