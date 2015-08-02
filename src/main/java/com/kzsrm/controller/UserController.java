package com.kzsrm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.kzsrm.model.User;
import com.kzsrm.service.UserService;
import com.kzsrm.utils.ApiCode;
import com.kzsrm.utils.JavaSmsApi;
import com.kzsrm.utils.MapResult;
import com.kzsrm.utils.SendMail;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/user")
public class UserController extends SimpleFormController {

	DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

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
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
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
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
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
			@RequestParam(value = "passwd", required = false) String passwd) {
		logger.info(phone + "   " + email + "   " + yzm + "   " + passwd);
		User user = new User();
		if (!StringUtils.isEmpty(phone)) {
			user.setPhone(phone);
		} else if (!StringUtils.isEmpty(email)) {
			user.setEmail(email);
		}

		if (passwd != null) {
			user.setPasswd(passwd);
			user.setCoin(300);// 默认300蚂蚁币
			user.setRegtime(new Date());
			try {
				return userService.insertUser(user);
			} catch (Exception e) {
				return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
			}
		} else {
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
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
		int res = userService.insertYZM(u);
		
		if(flag == true && res == 1){
			return map;
		}else{
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
		// 添加验证码
		User u = new User();
		if (phone != null) {
			u.setPasswd(user.getPhone());
		} else if (email != null) {
			u.setEmail(user.getEmail());
		}
		u.setYzm(yzm);
		u.setRegtime(new Date());
		return userService.insertUser(u);
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
			@RequestParam(value = "phone", required = false) String phone) throws IOException, URISyntaxException {
		 //修改为您的apikey.apikey可在官网（http://www.yuanpian.com)登录后用户中心首页看到
        String apikey = "2a622c3ef05c259f277c915f3b768f28";
        //修改为您要发送的手机号
        System.out.println(JavaSmsApi.getUserInfo(apikey));
        String text = "【蚂蚁课堂】您的验证码是"+SendMail.getCode();
        //发短信调用示例
        System.out.println(JavaSmsApi.sendSms(apikey, text, phone));
		Map<String, Object> map = MapResult.initMap();
		return map;
	}

	// springmvc日期处理
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
		super.initBinder(request, binder);
	}

	/**
	 * 个人资料完善(修改资料)
	 * 
	 * @param httpServletRequest
	 * @param user实体
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "updateUserInfo")
	@ResponseBody
	public Map<String, Object> updateUserInfo(HttpServletRequest httpServletRequest, User user) throws ParseException {
		if (user.getId() == 0) {
			return MapResult.initMap(ApiCode.PARG_ERR, "参数错误");
		}
		User u = new User();
		u.setId(user.getId());
		u.setName(user.getName());
		u.setAge(user.getAge());
		u.setSex(user.getSex());
		u.setPasswd(user.getPasswd());
		u.setBirthday(user.getBirthday());
		u.setQq(user.getQq());
		u.setAvator(user.getAvator());
		u.setSign(user.getSign());
		return userService.updateUser(u);
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
	 * @param para(手机或者邮箱)
	 * @return
	 */
	@RequestMapping(value = "userUnique")
	@ResponseBody
	public Map<String, Object> userUnique(HttpServletRequest httpServletRequest,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "phone", required = false) String phone) {
		return userService.selectUniqueUser(email,phone);
	}
}
