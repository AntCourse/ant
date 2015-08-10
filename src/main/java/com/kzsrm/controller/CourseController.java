package com.kzsrm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kzsrm.model.Course;
import com.kzsrm.model.User;
import com.kzsrm.service.CourseService;
import com.kzsrm.utils.MapResult;

@Controller
@RequestMapping("/cour")
public class CourseController {
	private static Logger logger = LoggerFactory.getLogger(User.class);

	@Resource private CourseService courseService;

	/**
	 * 课程列表
	 * @param pid		父级id，为空时查询顶层结点
	 * @param type		预留参数
	 * @return
	 */
	@RequestMapping(value = "/getCourList")
	@ResponseBody
	public Map<String, Object> getCourList(
			@RequestParam(required = false) String pid,
			@RequestParam(required = false) String type) {
		try{
			Map<String, Object> ret = MapResult.initMap();
			List<Course> courseList = courseService.getchildrenCour(pid, type);
			ret.put("courseList", courseList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
}
