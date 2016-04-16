package com.kzsrm.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kzsrm.model.Goods;
import com.kzsrm.service.GoodsService;
import com.kzsrm.utils.ComUtils;
import com.kzsrm.utils.MapResult;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	private static Logger logger = LoggerFactory.getLogger(GoodsController.class);
	JsonConfig goodsCf = ComUtils.jsonConfig(new String[]{"id","reDate"});
	
	@Resource private GoodsService goodsService;

	/**
	 * 商品列表
	 * @param type			1-名师教程，2-体系课程
	 * @param pageIndex		页数
	 * @param pageSize		页大小
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGoodsList")
	public Map<String, Object> getGoodsList(
			@RequestParam(defaultValue="1") int pageIndex,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(required = true) String type) {
		try{
			Map<String, Object> ret = MapResult.initMap();
			List<Goods> goodsList = goodsService.getList(type, pageIndex, pageSize);
			
			ret.put("result", JSONArray.fromObject(goodsList, goodsCf));
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
}
