package com.kzsrm.controller;

import java.util.Date;
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
import com.kzsrm.utils.ApiCode;
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
	 * @return User对象
	 */
	@RequestMapping(value = "/queryUser")
	@ResponseBody
	public Map<String, Object> queryUser(int id) {
		Map<String, Object> result = MapResult.initMap();
		result.put("data", userService.selectUser(id));
		return result;
	}

	/**
	 * 用户注册
	 * 
	 * @param httpServletRequest
	 * @param phone
	 * @param email
	 * @param yzm
	 * @param passwd
	 * @return
	 */
	@RequestMapping(value = "/reg")
	@ResponseBody
	public Map<String, Object> reg(HttpServletRequest httpServletRequest,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "yzm", required = false) String yzm,
			@RequestParam(value = "passwd", required = true) String passwd) {
		logger.info(phone + "   " + email + "   " + yzm + "   " + passwd);
		if ((StringUtils.isEmpty(phone) || StringUtils.isEmpty(email)) && StringUtils.isEmpty(passwd)) {
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
		User user = new User();
		user.setPhone(phone);
		user.setEmail(email);
		user.setPasswd(passwd);
		user.setCoin(300);
		user.setRegtime(new Date());
		try {
			return userService.insertUser(user);
		} catch (Exception e) {
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
	}
}
