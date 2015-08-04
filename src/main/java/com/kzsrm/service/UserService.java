package com.kzsrm.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kzsrm.model.Sign;
import com.kzsrm.model.User;
import com.kzsrm.model.Yzm;

public interface UserService {

//	public Map<String, Object> createUser(String name, Integer age, String sex, String phone, String passwd,
//			String email, String sign, String tag, String status, String appv, String src);

	public Map<String, Object> selectUser(int id);

	public Map<String, Object> insertUser(User user);

	public Map<String, Object> updateUser(User user);

	public Map<String, Object> selectUniqueUser(String email, String phone);

	public int insertYZM(User user);

	public Yzm getYzm(String email, String phone);

	public Map<String, Object> login(User user);

	public boolean insertSign(Sign sign);

	public Sign getSign(int uid);
	
	public int updateSign(Sign sign);
	
	public User selByEmailOrMobile(String email,String mobile);
	
}
