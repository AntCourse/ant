package com.kzsrm.serviceImpl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.UserDao;
import com.kzsrm.model.User;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.UserService;
import com.kzsrm.utils.MapResult;



@Service("userService")
@Transactional
public class UserServiceImpl extends BaseServiceMybatisImpl<User,Integer> implements UserService {
//public class UserServiceImpl implements UserService {
	
	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserDao userDao;
	
	@Override
	protected EntityDao<User, Integer> getEntityDao() {
	// TODO Auto-generated method stub
	return userDao;
	}
	
	
	public Map<String, Object> createUser(String name, Integer age, String sex,
			String phone, String passwd, String email, String sign, String tag,
			String status, String appv, String src) {
		
		
		// TODO Auto-generated method stub
		//age = 1;
		User u = new User();
		u.setName(name);
		u.setPhone(phone);
		u.setPasswd(passwd);
		u.setAge(0);
		
		this.userDao.saveEntity(u);
		//this.save(u);
		Map<String, Object> map = MapResult.initMap();
		map.put("uid", u.getId());
		map.put("data",u);
		
		return map;
	}


	@Override
	public Map<String, Object> selectUser(int id) {
		Map<String, Object> map = MapResult.initMap();
		map.put("data", this.userDao.getById(id));
		return map ;
	}


	@Override
	public Map<String, Object> insertUser(User user) {
		Map<String, Object> map = MapResult.initMap();
		this.userDao.saveEntity(user);
		map.put("data",user);
		return map;
	}
}
