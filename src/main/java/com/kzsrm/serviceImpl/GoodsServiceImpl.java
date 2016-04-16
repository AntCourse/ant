package com.kzsrm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.GoodsDao;
import com.kzsrm.model.Goods;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.GoodsService;

@Service
@Transactional
public class GoodsServiceImpl extends BaseServiceMybatisImpl<Goods, String> implements GoodsService {
	@Resource
	private GoodsDao<?> goodsDao;

	@Override
	protected EntityDao<Goods, String> getEntityDao() {
		return goodsDao;
	}

	/**
	 * 获取商品列表
	 * @param type			类型
	 * @param pageIndex		页数
	 * @param pageSize		页大小
	 * @return
	 */
	public List<Goods> getList(String type, int pageIndex, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("pageIndex", (pageIndex-1)*pageSize);
		map.put("pageSize", pageSize);
		return goodsDao.getList(map);
	}


}
