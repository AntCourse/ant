package com.kzsrm.service;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.PointLog;

public interface PointLogService  extends BaseServiceMybatis<PointLog, String> {
	/**
	 * 判断当前知识点用户是否已学
	 * @param pid		知识点id
	 * @param userId	用户
	 * @return
	 */
	String checkIsLearn(String pid, String userId);

}
