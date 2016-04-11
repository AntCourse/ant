package com.kzsrm.dao;

import org.springframework.stereotype.Repository;

import com.kzsrm.model.Goods;
import com.kzsrm.mybatis.BaseMybatisDao;

@Repository
public class GoodsDao<E> extends BaseMybatisDao<Goods, String> {
	
	public String getMybatisMapperNamesapce() {
		return "com.kzsrm.model.GoodsMapper";
	}

}