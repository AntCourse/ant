package com.kzsrm.serviceImpl;

import java.util.Date;
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
	public User selectUser(int id) {
		User user = this.userDao.getUserBySessionId(id);
		return user;
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
	public Map<String, Object> selectUniqueUser(String phone) {
		User user = this.userDao.selectIsExitUser(phone);
		map.put("data", user);
		return map;
	}

	@Override
	public int insertYZM(User user) {
		return this.userDao.insertYZM(user);
	}

	@Override
	public Yzm getYzm(String phone) {
		return this.userDao.selectOneYzm(phone);
	}

	@Override
	public Map<String, Object> login(User user) {
		User u = this.userDao.userLogin(user);
		if (u != null) {
			map.put("data", u);
			// 修改最后一次登录时间、
			User useras = new User();
			useras.setLogintime(new Date());
			userDao.update(useras);
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
	public Sign getSign(String phone) {
		Sign sign = this.userDao.getSign(phone);
		return sign;
	}

	@Override
	public int updateSign(Sign sign) {
		int result = this.userDao.updateSign(sign);
		return result;
	}

	@Override
	public User selByEmailOrMobile(String mobile) {
		return this.userDao.getUserByEmailOrMobile(mobile);
	}

	@Override
	public int batchQuartz() {
		return this.userDao.getBatchQuartz();
	}
}
