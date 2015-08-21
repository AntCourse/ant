package com.kzsrm.dao;

import java.io.Reader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.kzsrm.model.Sign;
import com.kzsrm.model.User;
import com.kzsrm.model.Yzm;
import com.kzsrm.mybatis.BaseMybatisDao;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.utils.MapResult;
import com.kzsrm.utils.Tools;

@Repository("userDao")
public class UserDao<E> extends BaseMybatisDao<User, Integer> {
	// public class UserDao extends SqlSessionDaoSupport {

	private static SqlSessionFactory sqlSessionFactory;

	private static final String yzm = "com.kzsrm.model.YzmMapper";
	private static final String sign = "com.kzsrm.model.SignMapper";
	Map<String, Object> map = MapResult.initMap();

	public String getMybatisMapperNamesapce() {
		// TODO Auto-generated method stub
		return "com.kzsrm.model.UserMapper";
	}

	public <E> void saveEntity(E entity) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(entity);
		// map.put("name", name);
		// map.put("phone", phone);
		// map.put("passwd", passwd);\
		System.out.println("this.getMybatisMapperNamesapce()   " + this.getMybatisMapperNamesapce());
		this.getSqlSession().insert(this.getMybatisMapperNamesapce() + ".insert", entity);
	}

	public void save(String name, int age, String sex, String phone, String passwd, String email, String sign,
			String tag, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("phone", phone);
		map.put("passwd", passwd);
		this.getSqlSession().insert(this.getMybatisMapperNamesapce() + ".insert", map);
	}

	public int insertYZM(User user) {
		return this.getSqlSession().insert(yzm + ".insertYZM", user);
	}

	public User selectIsExitUser(String email, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("phone", phone);
		User user = this.getSqlSession().selectOne(this.getMybatisMapperNamesapce() + ".selectUser", map);
		return user;
	}

	public Yzm selectOneYzm(String email, String phone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("phone", phone);
		return this.getSqlSession().selectOne(yzm + ".selectOneYzm", map);
	}

	public User userLogin(User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", user.getEmail());
		map.put("phone", user.getPhone());
		map.put("passwd", user.getPasswd());
		return this.getSqlSession().selectOne(this.getMybatisMapperNamesapce() + ".userLogin", map);
	}

	/*
	 * 新增用户打卡记录
	 */
	public int insertSign(int uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("startSignDay", Tools.ymd.format(new Date()));
		map.put("lastSignDay", Tools.ymd.format(new Date()));
		map.put("continueSignDay", 1);
		map.put("status", "1");
		System.out.println(map);
		return this.getSqlSession().insert(sign + ".insertSign", map);

	}
	
	/*
	 * 修改用户打卡记录
	 */
	public int updateSign(int uid,int status) {
		map.put("uid", uid);
		map.put("status", status);
		map.put("lastSignDay", Tools.ymd.format(new Date()));
		System.out.println(map);
		return this.getSqlSession().insert(sign + ".updateSign", map);

	}

	/*
	 * 获取用户打卡数据
	 */
	public Sign getSign(int uid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", uid);
//		map.put("checkTimes_Last", Tools.ymd.format(new Date()));
		return this.getSqlSession().selectOne(sign+".getSignNum", map);
	}
}
