package com.kzsrm.service;

import java.util.List;

import com.kzsrm.baseservice.BaseServiceMybatis;
import com.kzsrm.model.Organization;

public interface OrganizationService  extends BaseServiceMybatis<Organization, String> {

	/**
	 * 查询所有机构信息
	 * @param pid
	 * @param type
	 * @return
	 */
	List<Organization> getAllOrganization();

}
