package com.kzsrm.service;

import java.util.List;
import java.util.Map;

import com.kzsrm.model.User;
import com.kzsrm.model.Yzm;

public interface UserService {

	public Map<String, Object> createUser(String name, Integer age, String sex, String phone, String passwd,
			String email, String sign, String tag, String status, String appv, String src);

	public Map<String, Object> selectUser(int id);

	public Map<String, Object> insertUser(User user);

	public Map<String, Object> updateUser(User user);

	public Map<String, Object> selectUniqueUser(String email, String phone);

	public int insertYZM(User user);

	public Yzm getYzm(String email, String phone);

}
