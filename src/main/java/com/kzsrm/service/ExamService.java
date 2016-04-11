package com.kzsrm.service;

import java.util.List;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Examination;

public interface ExamService  extends BaseServiceMybatis<Examination, String> {
	/**
	 * 获取模考列表
	 * @param cid	课程id
	 * @return
	 */
	List<Examination> getListByCour(String cid);

}
