package com.kzsrm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kzsrm.model.CheckInfo;
import com.kzsrm.model.Organization;
import com.kzsrm.model.Teacher;
import com.kzsrm.model.TeacherOrg;
import com.kzsrm.service.CheckInService;
import com.kzsrm.service.OrganizationService;
import com.kzsrm.service.TeacherOrgService;
import com.kzsrm.service.TeacherService;
import com.kzsrm.utils.MapResult;

@Controller
@RequestMapping("/organization")
public class OrganizationController {
	private static Logger logger = LoggerFactory.getLogger(Organization.class);

	@Resource
	private OrganizationService organizationService;

	@Resource
	private TeacherOrgService teacherOrgService;

	@Resource
	private TeacherService teacherService;
	
	@Resource
	private CheckInService checkinService;

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

	/**
	 * 获取机构下所有教师
	 * 
	 * @param oid
	 *            机构id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeacherListByOrgId")
	public Map<String, Object> getTeacherListByOrgId(
			@RequestParam(required = true) String orgId) {
		try {
			Map<String, Object> ret = MapResult.initMap();
			List<TeacherOrg> organizationList = teacherOrgService
					.getListByOrgId(orgId);
			List<Teacher> teacherList = new ArrayList<Teacher>();
			for (TeacherOrg teacherOrg : organizationList) {
				Teacher t = teacherService.getTeacherById(teacherOrg.getTid());
				if(t!=null){
					teacherList.add(t);
				}
			}
			ret.put("result", teacherList);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	/**
	 * 向机构报名
	 * 
	 * @param userId
	 *            userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkin")
	public Map<String, Object> checkin(@RequestParam(required = true) String orgId,
			@RequestParam(required = true) String checkinTime,@RequestParam(required = true) String userPhone,
			@RequestParam(required = true) String type,@RequestParam(required = true) String userLocal) {
		try {
			CheckInfo checkInfo = new CheckInfo();
			checkInfo.setOrgId(Integer.parseInt(orgId));
			checkInfo.setCheckinTime(checkinTime);
			checkInfo.setUserLocal(userLocal);
			checkInfo.setType(Integer.parseInt(type));
			checkInfo.setUserPhone(userPhone);
			if(checkinService.getInfoByUserPhoneAndOrg(userPhone, Integer.parseInt(orgId))==null){
				if(checkinService.checkIn(checkInfo)==1){
					return MapResult.initMap();
				}else{
					return MapResult.initMap(-1, "数据存储错误");
				}
			}else{
				return MapResult.initMap(-1, "您已报名过");
			}
			
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	/**
	 * 取消向机构报名
	 * 
	 * @param userId
	 *            userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelCheckIn")
	public Map<String, Object> cancelCheckIn(@RequestParam(required = true) String checkId) {
		try {
			if(checkinService.deleteById(Integer.parseInt(checkId))==1){
				return MapResult.initMap();
			}else{
				return MapResult.initMap(-1, "取消失败");
			}
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	/**
	 * 查看报名机构列表
	 * 
	 * @param userId
	 *            userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getListByPhone")
	public Map<String, Object> getListByPhone(@RequestParam(required = true) String userPhone) {
		try {
			
			Map<String, Object> ret = MapResult.initMap();
			List<CheckInfo> infoList = checkinService.getListByUserPhone(userPhone);
			List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String, Object>>();
			for(CheckInfo checkInfo : infoList){
				Organization org = organizationService.getOrganizationById(checkInfo.getOrgId());
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("orgId", org.getId());
				map.put("orgName", org.getName());
				map.put("orgPic", org.getLogo());
				map.put("checkId", checkInfo.getId());
				map.put("userPhone", checkInfo.getUserPhone());
				map.put("userLocal", checkInfo.getUserLocal());
				map.put("type", checkInfo.getType());
				map.put("checkinTime", checkInfo.getCheckinTime());
				resultList.add(map);
			}
			
			ret.put("result", resultList);
			return ret;			
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	/**
	 * 查看报名人信息
	 * 
	 * @param userId
	 *            userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfoByUserPhoneAndOrg")
	public Map<String, Object> getInfoByUserPhoneAndOrg(@RequestParam(required = true) String userPhone,
			@RequestParam(required = true) String orgId) {
		try {
			
			Map<String, Object> ret = MapResult.initMap();
			CheckInfo info = checkinService.getInfoByUserPhoneAndOrg(userPhone, Integer.parseInt(orgId));
			ret.put("result", info);
			return ret;			
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	/**
	 * 更新关注次数
	 * 
	 * @param orgId
	 *            orgId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAttendCount")
	public Map<String, Object> updateAttendCount(@RequestParam(required = true) String orgId) {
		try {
			
			Map<String, Object> ret = MapResult.initMap();
			organizationService.updateAttendCount(orgId);
			return ret;			
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
	/**
	 * 更新报到次数
	 * 
	 * @param orgId
	 *            orgId
	 * @param type
	 *            1 报到 0 取消报到
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCheckInCount")
	public Map<String, Object> updateCheckInCount(@RequestParam(required = true) String orgId,
			@RequestParam(required = true) String type) {
		try {
			
			Map<String, Object> ret = MapResult.initMap();
			organizationService.updateCheckInCount(orgId, type);
			return ret;			
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}

}
