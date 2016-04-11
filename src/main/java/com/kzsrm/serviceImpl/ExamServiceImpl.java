package com.kzsrm.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.ExaminationDao;
import com.kzsrm.model.Examination;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.ExamService;

@Service
@Transactional
public class ExamServiceImpl extends BaseServiceMybatisImpl<Examination, String> implements ExamService {
	@Resource
	private ExaminationDao<?> examinationDao;

	@Override
	protected EntityDao<Examination, String> getEntityDao() {
		return examinationDao;
	}

	/**
	 * 获取模考列表
	 * @param cid	课程id
	 * @return
	 */
	@Override
	public List<Examination> getListByCour(String cid) {
		return examinationDao.getListByCour(cid);
	}

}
