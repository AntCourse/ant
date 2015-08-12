package com.kzsrm.service;

import java.util.List;
import java.util.Map;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Subject;

public interface SubjectService  extends BaseServiceMybatis<Subject, Integer> {

	/**
	 * 获取视频对应的测试题
	 * @param videoId
	 * @return
	 */
	List<Subject> getSubjectByVideo(String videoId);
	/**
	 * 校验测试题的答案
	 * @param answer
	 * @return
	 */
	List<Map<String, Object>> checkAnswer(String answer);

}
