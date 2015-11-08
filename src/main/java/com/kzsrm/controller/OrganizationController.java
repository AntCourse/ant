package com.kzsrm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kzsrm.model.Organization;
import com.kzsrm.service.OrganizationService;
import com.kzsrm.utils.MapResult;

@Controller
@RequestMapping("/organization")
public class OrganizationController {
	private static Logger logger = LoggerFactory.getLogger(Organization.class);

	@Resource
	private OrganizationService organizationService;

	/**
	 * 课程列表-二级
	 * 
	 * @param pid
	 *            课程id，返回本级及其子集信息
	 * @param type
	 *            预留参数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllOrganization")
	public Map<String, Object> getAllOrganization() {
		try {
			Map<String, Object> ret = MapResult.initMap();
			List<Organization> organizationList = organizationService
					.getAllOrganization();
			ret.put("result", organizationList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}

}
