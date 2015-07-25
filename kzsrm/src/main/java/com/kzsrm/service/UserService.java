package com.kzsrm.service;

import java.util.Map;

import com.kzsrm.model.User;

public interface UserService {

	public Map<String, Object> createUser(String name, Integer age, String sex,
			String phone, String passwd, String email, String sign, String tag,
			String status, String appv, String src);
	
	public User selectUser(int id);

}
