package com.kzsrm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.UserDao;
import com.kzsrm.model.User;
import com.kzsrm.model.Yzm;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.UserService;
import com.kzsrm.utils.MapResult;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceMybatisImpl<User, Integer>implements UserService {
	// public class UserServiceImpl implements UserService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private UserDao<?> userDao;
	Map<String, Object> map = MapResult.initMap();

	@Override
	protected EntityDao<User, Integer> getEntityDao() {
		return userDao;
	}

	public Map<String, Object> createUser(String name, Integer age, String sex, String phone, String passwd,
			String email, String sign, String tag, String status, String appv, String src) {

		User u = new User();
		u.setName(name);
		u.setPhone(phone);
		u.setPasswd(passwd);
		u.setEmail(email);
		u.setAge(0);

		this.userDao.saveEntity(u);
		// this.save(u);
		map.put("uid", u.getId());
		map.put("data", u);

		return map;
	}

	@Override
	public Map<String, Object> selectUser(int id) {
		map.put("data", this.userDao.getById(id));
		return map;
	}

	@Override
	public Map<String, Object> insertUser(User user) {
		this.userDao.saveEntity(user);
		this.save(user);
		map.put("data", user);
		return map;
	}

	@Override
	public Map<String, Object> updateUser(User user) {
		this.userDao.update(user);
		map.put("data", user);
		return map;
	}

	@Override
	public Map<String, Object> selectUniqueUser(String email,String phone) {
		User user  = this.userDao.selectIsExitUser(email,phone);
		map.put("data", user);
		return map;
	}

	@Override
	public int insertYZM(User user) {
		return this.userDao.insertYZM(user);
	}

	@Override
	public Yzm getYzm(String email,String phone) {
		return this.userDao.selectOneYzm(email,phone);
	}
}
