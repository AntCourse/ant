package com.kzsrm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kzsrm.model.User;
import com.kzsrm.model.Yzm;
import com.kzsrm.service.UserService;
import com.kzsrm.utils.ApiCode;
import com.kzsrm.utils.ConfigUtil;
import com.kzsrm.utils.JavaSmsApi;
import com.kzsrm.utils.MapResult;
import com.kzsrm.utils.SendMail;
import com.kzsrm.utils.Tools;
import com.sun.mail.imap.protocol.MailboxInfo;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/user")
public class UserController extends SimpleFormController {

	@Resource
	private UserService userService;

	private static Logger logger = LoggerFactory.getLogger(User.class);

	/**
	 * 用户注册
	 * 
	 * @param httpServletRequest
	 * @param phone
	 * @param email
	 * @param yzm
	 * @param passwd
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/reg")
	@ResponseBody
	public Map<String, Object> reg(HttpServletRequest httpServletRequest,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "yzm", required = false) String yzm,
			@RequestParam(value = "passwd", required = false) String passwd) throws ParseException {
		// 查询是否已经注册过
		Map<String, Object> map = null;
		try {
			map = userService.selectUniqueUser(email, phone);
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
		// 获取最新验证码
		Yzm yzmList = null;
		try {
			yzmList = userService.getYzm(email, phone);
			Date d = yzmList.getRegtime();
			String date = Tools.ymdhms.format(d);
			boolean isCodeInvalid = false;
			User u = new User();
			if (phone != null) {
				try {
					isCodeInvalid = Tools.codeInvalid(date, 1);
				} catch (ParseException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			} else if (email != null) {
				try {
					isCodeInvalid = Tools.codeInvalid(date, 0);
				} catch (ParseException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}
			if (isCodeInvalid == true) {
				return MapResult.initMap(ApiCode.CODE_INVALID, "验证码已过期");
			} else {
				if (!StringUtils.isEmpty(yzm)) {
					if (yzmList.getYzm().equals(yzm)) {
						if (map.get("data") == null) {
							if (!StringUtils.isEmpty(phone)) {
								u.setPhone(phone);
							} else if (!StringUtils.isEmpty(email)) {
								u.setEmail(email);
							}
							u.setRegtime(new Date());
							u.setPasswd(passwd);
							u.setCoin(300);// 默认300蚂蚁币
							u.setRegtime(new Date());
							return userService.insertUser(u);
						} else {
							return MapResult.initMap(ApiCode.PARG_ERR, "已有此数据");
						}
					} else {
						return MapResult.initMap(ApiCode.PARG_ERR, "验证码错误");
					}
				} else {
					return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
				}
			}
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
	}

	/**
	 * 根据id修改用户信息
	 * 
	 * @param httpServletRequest
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest httpServletRequest, User user) {
		if (StringUtils.isEmpty(String.valueOf(user.getId()))) {
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
		try {
			return userService.updateUser(user);
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
		try {
			result.put("data", userService.selectUser(id));
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
		return result;
	}

	/**
	 * 发送邮箱验证码
	 * 
	 * 成功返回true 失败返回false
	 * 
	 * @return
	 * @throws MessagingException
	 */
	@RequestMapping(value = "getYzmByEmail")
	@ResponseBody
	public Map<String, Object> getYzmByEmail(HttpServletRequest httpServletRequest, User user,
			@RequestParam(value = "email", required = false) String email) throws MessagingException {
		StringBuffer sb = new StringBuffer("点击下面链接激活账号，2小时后失效，否则重新注册账号，链接只能使用一次，请尽快激活！");
		String code = SendMail.getCode();
		sb.append("<a href=\"http://localhost:8080/kzsrm/user/mailActive?email=");
		sb.append(email);
		sb.append("&yzm=");
		sb.append(code);
		sb.append("\"></br>http://localhost:8080/kzsrm/user/mailActive?email=");
		sb.append(email);
		sb.append("&yzm=");
		sb.append(code);
		sb.append("</a>");
		boolean flag = false;
		Map<String, Object> map = MapResult.initMap();
		if (email != null) {
			flag = SendMail.sendMail(email, "", "", "", sb.toString());
		} else {
			map = MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
		// 添加验证码
		User u = new User();
		if (email != null) {
			u.setEmail(user.getEmail());
		}
		u.setYzm(code);
		u.setRegtime(new Date());
		int res = 0;
		try {
			res = userService.insertYZM(u);
		} catch (Exception e) {
			return MapResult.failMap();
		}

		if (flag == true && res == 1) {
			return map;
		} else {
			map = MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
		return map;
	}

	/**
	 * 邮箱激活验证码
	 * 
	 * @param httpServletRequest
	 * @param phone
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "mailActive")
	@ResponseBody
	public Map<String, Object> mailActive(HttpServletRequest httpServletRequest, User user,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "yzm", required = false) String yzm) {

		// 查询是否已经注册过,防止重复点击邮件链接
		Map<String, Object> map = null;
		try {
			map = userService.selectUniqueUser(email, phone);
		} catch (Exception e) {
			return MapResult.failMap();
		}
		if (map.get("data") == null) {
			// 获取验证码发送时间
			Yzm yzmList = userService.getYzm(email, phone);
			String str = yzmList.getYzm();
			boolean isCodeInvalid = false;
			User u = new User();
			String time = Tools.ymdhms.format(yzmList.getRegtime());
			if (isCodeInvalid == true) {
				return MapResult.initMap(ApiCode.CODE_INVALID, "验证码已过期");
			} else {
				if (str.equals(yzm)) {
					if (phone != null) {
						u.setPasswd(user.getPhone());
						try {
							isCodeInvalid = Tools.codeInvalid(time, 1);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else if (email != null) {
						u.setEmail(user.getEmail());
						try {
							isCodeInvalid = Tools.codeInvalid(time, 0);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					u.setYzm(yzm);
					u.setRegtime(new Date());
					return userService.insertUser(u);
				} else {
					return MapResult.initMap(ApiCode.CODE_INVALID, "验证码已过期");
				}
			}
		} else {
			return MapResult.initMap();
		}
	}

	/**
	 * 发送手机验证码
	 * 
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@RequestMapping(value = "getYzmByPhone")
	@ResponseBody
	public Map<String, Object> getYzmByPhone(HttpServletRequest httpServletRequest,
			@RequestParam(value = "mobile", required = false) String mobile) throws IOException, URISyntaxException {
		String apikey = ConfigUtil.getStringValue("sms.key");
		String code = SendMail.getCode();
		System.out.println(JavaSmsApi.getUserInfo(apikey));
		String text = "【蚂蚁课堂】您的验证码是" + code;
		String result = JavaSmsApi.sendSms(apikey, text, mobile);
		JSONObject jo = JSONObject.parseObject(result);
		String msg = jo.getString("msg");
		if (msg.equals("OK")) {
			User u = new User();
			u.setPhone(mobile);
			u.setYzm(code);
			u.setRegtime(new Date());
			int res = 0;
			try {
				res = userService.insertYZM(u);
			} catch (Exception e) {
				return MapResult.failMap();
			}
			if (res == 1)
				return MapResult.initMap();
			else
				return MapResult.initMap();
		} else {
			return MapResult.failMap();
		}
	}

	// 日期处理
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(Tools.ymd, true);
		binder.registerCustomEditor(Date.class, dateEditor);
		super.initBinder(request, binder);
	}

	/**
	 * 修改密码
	 * 
	 * @param httpServletRequest
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "updateUserPwd")
	@ResponseBody
	public Map<String, Object> updateUserPwd(HttpServletRequest httpServletRequest,
			@RequestParam(value = "id", required = true) String id) {

		return null;
	}

	/**
	 * 签到
	 * 
	 * @param httpServletRequest
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "userSignIn")
	@ResponseBody
	public Map<String, Object> userSignIn(HttpServletRequest httpServletRequest,
			@RequestParam(value = "id", required = true) String id) {

		return null;
	}

	/**
	 * 用户唯一性验证(手机和邮箱)
	 * 
	 * @param httpServletRequest
	 * @return true 有此用户 false不存在该用户
	 */
	@RequestMapping(value = "userUnique")
	@ResponseBody
	public Map<String, Object> userUnique(HttpServletRequest httpServletRequest,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "mobile", required = false) String mobile) {
		try {
			Map<String, Object> user = userService.selectUniqueUser(email, mobile);
			Map<String, Object> map = MapResult.initMap();
			if (user.get("data") == null) {
				map.put("data", "false");
			} else {
				map.put("data", "true");
			}
			return map;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
}
