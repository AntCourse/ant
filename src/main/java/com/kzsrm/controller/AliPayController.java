package com.kzsrm.controller;

import java.net.URLEncoder;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import com.kzsrm.model.Course;
import com.kzsrm.utils.MapResult;

@Controller
@RequestMapping("/aliPay")
public class AliPayController {
	private static Logger logger = LoggerFactory.getLogger(Course.class);
	
	/**
	 * 订单信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrderInfo")
	public Map<String, Object> getOrderInfo(@RequestParam(required = true) String price) {
		try{

			String orderInfo = AlipayCore.getOrderInfo("支付宝支付",
					"用于测试支付宝快捷支付测试！", price);
			String sign = AlipayCore.sign(orderInfo, AlipayConfig.private_key);
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");

			String orderString = orderInfo + "&sign=\"" + sign + "\"&"
					+ AlipayCore.getSignType();
			Map<String, Object> ret = MapResult.initMap();
			ret.put("result", orderString);
			return ret;
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
}
