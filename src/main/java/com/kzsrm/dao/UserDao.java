package com.kzsrm.dao;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.kzsrm.model.User;
import com.kzsrm.mybatis.BaseMybatisDao;
import com.kzsrm.mybatis.EntityDao;

@Repository("userDao")
public class UserDao<E> extends BaseMybatisDao<User, Integer> {
//public class UserDao extends SqlSessionDaoSupport {
		
	
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	public String getMybatisMapperNamesapce() {
		// TODO Auto-generated method stub
		return "com.kzsrm.model.UserMapper";
	}
	
	public <E> void saveEntity(E entity){
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println(entity);
		//map.put("name", name);
		//map.put("phone", phone);
		//map.put("passwd", passwd);
		this.getSqlSession().insert(this.getMybatisMapperNamesapce()+".insert",entity);
	}
	
	public void save(String name, int age, String sex,String phone, String passwd, String email, String sign, String tag,
			String status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("phone", phone);
		map.put("passwd", passwd);
		this.getSqlSession().insert(this.getMybatisMapperNamesapce()+".insert",map);
	}
}
