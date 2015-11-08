package com.kzsrm.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.OrganizationDao;
import com.kzsrm.model.Organization;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.OrganizationService;

@Service
@Transactional
public class OrganizationServiceImpl extends BaseServiceMybatisImpl<Organization, String> implements OrganizationService {
	@Resource
	private OrganizationDao<?> organizationDao;

	@Override
	protected EntityDao<Organization, String> getEntityDao() {
		return organizationDao;
	}

	/**
	 * 查询机构信息
	 * @param pid
	 * @param type
	 * @return
	 */
	@Override
	public List<Organization> getAllOrganization(){
		return organizationDao.getAllOrganization();
	}

}
