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

import com.kzsrm.model.Examination;
import com.kzsrm.model.Subject;
import com.kzsrm.service.ExamService;
import com.kzsrm.service.SubjectService;
import com.kzsrm.utils.ApiCode;
import com.kzsrm.utils.ComUtils;
import com.kzsrm.utils.MapResult;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/exam")
public class ExamController {
	private static Logger logger = LoggerFactory.getLogger(ExamController.class);
	JsonConfig examCf = ComUtils.jsonConfig(new String[]{"id","reDate"});
	
	@Resource private ExamService examService;
	@Resource private SubjectService subjectService;

	/**
	 * 模考列表
	 * @param courseId		课程id
	 * @param type			1-题库，2-真题
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getExamList")
	public Map<String, Object> getExamList(
			@RequestParam(required = true) String courseId,
			@RequestParam(required = true) String type) {
		try{
			if (StringUtils.isBlank(courseId))
				return MapResult.initMap(ApiCode.PARG_ERR, "课程id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Examination> examList = examService.getListByCour(courseId, type);
			
			ret.put("result", JSONArray.fromObject(examList, examCf));
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	/**
	 * 获取模考对应的测试题
	 * @param examId		模考id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getExamSubList")
	public Map<String, Object> getExamSubList(
			@RequestParam(required = true) String examId) {
		try{
			if (StringUtils.isBlank(examId))
				return MapResult.initMap(ApiCode.PARG_ERR, "模考id为空");
			
			Map<String, Object> ret = MapResult.initMap();
			List<Subject> subList = subjectService.getSubjectByExam(examId);
			ret.put("result", subList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
}
