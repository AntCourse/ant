package com.kzsrm.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kzsrm.model.User;
import com.kzsrm.service.UserService;
import com.kzsrm.utils.MapResult;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	private static Logger logger = LoggerFactory.getLogger(User.class);

	@RequestMapping(value = "/create")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest httpServletRequest,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "sex", required = false) String sex,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "passwd", required = true) String passwd,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "sign", required = false) String sign,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "appv", required = false) String appv,
			@RequestParam(value = "src", required = false) String src) {

		logger.info(
				"user add params--> name:{}, age:{}, sex:{}, phone:{}, "
						+ "passwd:{}, email:{}, sign:{}, tag:{},status:{}, appv:{}," + "src:{}",
				new Object[] { name, age, sex, phone, passwd, email, sign, tag, status, appv, src });

		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(passwd)) {
			return MapResult.initMap(1001, "参数错误");
		}

		try {
			return userService.createUser(name, age, sex, phone, passwd, email, sign, tag, status, appv, src);
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest httpServletRequest,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "sex", required = false) String sex,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "passwd", required = true) String passwd,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "sign", required = false) String sign,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "appv", required = false) String appv,
			@RequestParam(value = "src", required = false) String src) {

		logger.info(
				"user add params--> name:{}, age:{}, sex:{}, phone:{}, "
						+ "passwd:{}, email:{}, sign:{}, tag:{},status:{}, appv:{}," + "src:{}",
				new Object[] { name, age, sex, phone, passwd, email, sign, tag, status, appv, src });

		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(passwd)) {
			return MapResult.initMap(1001, "参数错误");
		}

		try {
			return userService.createUser(name, age, sex, phone, passwd, email, sign, tag, status, appv, src);
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}

	/**
	 * 根据id查询用户的信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryUser")
	@ResponseBody
	public Map<String, Object> queryUser(int id) {
		System.out.println("id    " + id);
		User user = userService.selectUser(id);
		Map<String, Object> result = null;
		System.out.println(user == null);
		result = MapResult.initMap();
		result.put("data", user);
		System.out.println(result);
		return result;
	}

}
