package com.kzsrm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
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
import com.alibaba.fastjson.JSONObject;
import com.kzsrm.model.Sign;
import com.kzsrm.model.User;
import com.kzsrm.model.Yzm;
import com.kzsrm.service.UserService;
import com.kzsrm.utils.ApiCode;
import com.kzsrm.utils.DateUtil;
import com.kzsrm.utils.MapResult;
import com.kzsrm.utils.SendMail;
import com.kzsrm.utils.Tools;

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
							u.setRegtime(new Date());
							Map<String, Object> maps = userService.insertUser(u);
							//查询用户
							User user = userService.selByEmailOrMobile(email, phone);
							Sign sign = new Sign();
							sign.setAntCoin(300);
							sign.setUid(user.getId());
							boolean flag = userService.insertSign(sign);
							return maps.get("data").equals("1") && flag == true ?MapResult.initMap():MapResult.failMap();
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
	 * 用户登录(游客/会员)
	 * 
	 * @param httpServletRequest
	 * @param user
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest httpServletRequest, User user,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "passwd", required = false) String passwd) throws ParseException {
		User u = new User();
		if (phone != null) {
			u.setPhone(phone);
		} else if (email != null) {
			u.setEmail(email);
		}
		u.setId(user.getId());
		// 查询是否已经注册过,防止重复点击邮件链接
		Map<String, Object> map = null;
		try {
			map = userService.login(user);
		} catch (Exception e) {
			return MapResult.failMap();
		}
		if (map.get("data").equals("false")) {
			return userService.login(u);
		} else {
			try {
				// 修改最后一次登录时间
				u.setLogintime(new Date());
				userService.updateUser(u);
				u.setPasswd(passwd);
				return userService.login(u);
			} catch (Exception e) {
				logger.error("", e);
				return MapResult.failMap();
			}
		}
	}

	/**
	 * 根据id/邮箱/手机修改用户信息
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
		return emailZym(email, user);
	}

	/**
	 * 发送邮件验证码
	 * 
	 * @param email
	 * @param user
	 * @return
	 * @throws MessagingException
	 */
	public Map<String, Object> emailZym(String email, User user) throws MessagingException {
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
		Map<String, Object> map = null;
		try {
			// 查询是否已有此用户
			map = userService.selectUniqueUser(email, phone);
		} catch (Exception e) {
			return MapResult.failMap();
		}
		if (map.get("data") != null) {
			User u = new User();
			// 获取验证码发送时间
			Yzm yzmList = userService.getYzm(email, phone);
			String str = yzmList.getYzm();
			boolean isCodeInvalid = false;
			String time = Tools.ymdhms.format(yzmList.getRegtime());
			if (phone != null) {
				try {
					isCodeInvalid = Tools.codeInvalid(time, 1);
				} catch (ParseException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			} else if (email != null) {
				try {
					isCodeInvalid = Tools.codeInvalid(time, 0);
				} catch (ParseException e) {
					logger.error("", e);
					e.printStackTrace();
				}
			}
			if (isCodeInvalid == true) {
				return MapResult.initMap(ApiCode.CODE_INVALID, "验证码已过期");
			} else {
				if (str.equals(yzm)) {
					u.setYzm(yzm);
					u.setRegtime(new Date());
					return userService.insertUser(u);
				} else {
					return MapResult.initMap(ApiCode.CODE_INVALID, "验证码错误");
				}
			}
		}
		return MapResult.initMap(ApiCode.CODE_INVALID, "没有此用户");
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
		return this.phoneYam(mobile);
	}

	/**
	 * 发送手机验证码
	 * 
	 * @param mobile
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public Map<String, Object> phoneYam(String mobile) throws IOException, URISyntaxException {
		// String apikey = ConfigUtil.getStringValue("sms.key");
		// String code = SendMail.getCode();
		// System.out.println(JavaSmsApi.getUserInfo(apikey));
		// String text = "【蚂蚁课堂】您的验证码是" + code;
		// String result = JavaSmsApi.sendSms(apikey, text, mobile);
		/*
		 * 测试
		 */
		JSONObject jo = new JSONObject();
		jo.put("code", 0);
		jo.put("msg", "OK");
		JSONObject jo1 = new JSONObject();
		jo1.put("nick", "蚂蚁课堂");
		jo1.put("gmt_created", "2015-07-29 15:15:24");
		jo1.put("mobile", "13240406688");
		jo1.put("email", "BD@antkt.com");
		jo1.put("ip_whitelist", "223.72.133.2");
		jo1.put("api_version", "v1");
		jo1.put("alarm_balance", 100);
		jo1.put("emergency_contact", "");
		jo1.put("emergency_mobile", "");
		jo1.put("balance", 981);

		jo.put("user", jo1);

		// JSONObject jo = JSONObject.parseObject(result);
		String msg = jo.getString("msg");

		if (msg.equals("OK")) {
			User u = new User();
			u.setPhone(mobile);
			u.setYzm("1919");
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
	 * @param user
	 * @param phone
	 * @param email
	 * @param passwd
	 * @param code
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws MessagingException
	 */
	@RequestMapping(value = "modifypasswd")
	@ResponseBody
	public Map<String, Object> modifypasswd(HttpServletRequest httpServletRequest, User user,
			@RequestParam(value = "phone", required = false) String phone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "passwd", required = false) String passwd,
			@RequestParam(value = "code", required = false) String code)
					throws IOException, URISyntaxException, MessagingException {
		// 获取最新验证码
		Yzm yzmList = null;
		try {
			yzmList = userService.getYzm(email, phone);
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}

		if (yzmList != null) {
			Date d = yzmList.getRegtime();
			String date = Tools.ymdhms.format(d);
			boolean isCodeInvalid = false;
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
				User u = new User();
				if (code.equals(yzmList.getYzm())) {
					if (phone != null) {
						u.setPasswd(passwd);
						u.setPhone(phone);
					} else if (email != null) {
						u.setPasswd(passwd);
						u.setEmail(email);
					}
					try {
						userService.updateUser(u);
					} catch (Exception e) {
						logger.error("", e);
						return MapResult.failMap();
					}
				} else {
					MapResult.initMap(ApiCode.PARG_ERR, "验证码错误");
				}
			}
		}
		return MapResult.failMap();
	}

	/**
	 * 签到
	 * 
	 * @param httpServletRequest
	 * @param id
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "userSignIn")
	@ResponseBody
	public Map<String, Object> userSignIn(HttpServletRequest httpServletRequest,
			@RequestParam(value = "uid", required = false) int uid) throws ParseException {
		// 查询该用户签到
		Sign listSign = userService.getSign(uid);
		if (listSign != null) {
			Sign s = new Sign();
			// 最后签到和当前差天数
			System.out.println(listSign.getLastSignDay() + "    " + new Date());
			int diffDay = DateUtil.getDifferSec(listSign.getLastSignDay(), new Date());
			if (diffDay == 0) {
				return MapResult.initMap(ApiCode.PARG_ERR, "今天已经签到啦");
			} else if (diffDay == 1) {
				int status = Integer.parseInt(listSign.getSignNum());
				if (status == 0) {
					status += 2;
					s.setSignNum(String.valueOf(Integer.parseInt(listSign.getSignNum()) + 2));
				} else {
					status += 1;
					s.setSignNum(String.valueOf(Integer.parseInt(listSign.getSignNum()) + 1));
				}
				int totalSignNum = 0;
				if (status == 1) {
					totalSignNum += 1;
				} else if (status == 2) {
					totalSignNum += 2;
				} else if (status == 3) {
					totalSignNum += 4;
				} else if (status == 4) {
					totalSignNum += 8;
				} else if (status == 5) {
					totalSignNum += 10;
				} else if (status == 6) {
					totalSignNum += 30;
				} else if (status > 6) {
					totalSignNum += 10;
				}
				if (status % 10 == 0) {
					totalSignNum += 30;
				}
				System.out.println("连续打卡赠送的币    " + totalSignNum);
				s.setUid(uid);
				s.setAntCoin(listSign.getAntCoin() + totalSignNum);
				
				userService.updateSign(s);
				return MapResult.initMap(ApiCode.PARG_ERR, "签到成功");
			} else {
				s.setUid(uid);
				s.setAntCoin(1);
				s.setSignNum("0");
				userService.updateSign(s);
				return MapResult.initMap(ApiCode.PARG_ERR, "签到过期  已经清零");
			}
		} else {
			try {
				Sign si = new Sign();
				si.setUid(uid);
				boolean insertSign = userService.insertSign(si);
				if (insertSign == true) {
					return MapResult.initMap(ApiCode.PARG_ERR, "签到成功");
				} else {
					return MapResult.initMap(ApiCode.PARG_ERR, "签到失败");
				}
			} catch (Exception e) {
				logger.error("", e);
				return MapResult.failMap();
			}
		}
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
