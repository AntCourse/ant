package com.kzsrm.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.SubjectLogDao;
import com.kzsrm.model.SubjectLog;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.SubjectLogService;

@Service
@Transactional
public class SubjectLogServiceImpl extends BaseServiceMybatisImpl<SubjectLog, String> implements SubjectLogService {
	@Resource
	private SubjectLogDao<?> subjectLogDao;
	
	@Override
	protected EntityDao<SubjectLog, String> getEntityDao() {
		return subjectLogDao;
	}
	
}
