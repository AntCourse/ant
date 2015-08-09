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

	@Resource
	private CourseService courseService;

	/**
	 * 课程列表
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/getCourList")
	@ResponseBody
	public Map<String, Object> getCourList(
			@RequestParam(required = false) String type) {
		List<Course> courseList = courseService.findAll();
		Map<String, Object> ret = MapResult.initMap();
		ret.put("courseList", courseList);
		return ret;
	}
}
