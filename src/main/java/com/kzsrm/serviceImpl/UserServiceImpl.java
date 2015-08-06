package com.kzsrm.serviceImpl;

import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.UserDao;
import com.kzsrm.model.Sign;
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

	@Override
	public Map<String, Object> selectUser(int id) {
		map.put("data", this.userDao.getById(id));
		return map;
	}

	@Override
	public Map<String, Object> insertUser(User user) {
		int i = this.userDao.saveEntity(user);
		map.put("data", i);
		return map;
	}

	@Override
	public Map<String, Object> updateUser(User user) {
		int result = this.userDao.update(user);
		boolean flag = false;
		if (result == 1) {
			flag = true;
		}
		map.put("data", flag);
		return map;
	}

	@Override
	public Map<String, Object> selectUniqueUser(String email, String phone) {
		User user = this.userDao.selectIsExitUser(email, phone);
		map.put("data", user);
		return map;
	}

	@Override
	public int insertYZM(User user) {
		return this.userDao.insertYZM(user);
	}

	@Override
	public Yzm getYzm(String email, String phone) {
		return this.userDao.selectOneYzm(email, phone);
	}

	@Override
	public Map<String, Object> login(User user) {
		User u = this.userDao.userLogin(user);
		if (u != null) {
			map.put("data", "true");
		} else {
			map.put("data", "false");
		}
		return map;
	}

	/*
	 * 添加打卡记录
	 */
	public boolean insertSign(Sign sign) {
		boolean flag = false;
		int result = this.userDao.insertSign(sign);
		if (result == 1) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/*
	 * 获取该用户是否打过卡
	 */
	public Sign getSign(String email,String phone) {
		Sign sign = this.userDao.getSign(email,phone);
		return sign;
	}

	@Override
	public int updateSign(Sign sign) {
		int result = this.userDao.updateSign(sign);
		return result;
	}

	@Override
	public User selByEmailOrMobile(String email, String mobile) {
		return this.userDao.getUserByEmailOrMobile(email, mobile);
	}
}
