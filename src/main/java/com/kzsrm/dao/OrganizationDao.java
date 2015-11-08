package com.kzsrm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Organization;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class OrganizationDao <E> extends BaseMybatisDao<Organization, String> {
	
	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.OrganizationMapper";
	}

	public List<Organization> getAllOrganization() {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".findAll");
	}

}
