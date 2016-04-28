package com.kzsrm.controller;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.wxpay.config.WXPayConfig;
import com.wxpay.util.CommonUtil;
import com.wxpay.util.PayCommonUtil;
import com.wxpay.util.TenpayUtil;
import com.wxpay.util.XMLUtil;

@Controller
@RequestMapping("/pay")
public class PayController {
	private static Logger logger = LoggerFactory.getLogger(Course.class);
	
	/**
	 * 支付宝订单信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAliOrderInfo")
	public Map<String, Object> getAliOrderInfo(@RequestParam(required = true) String body,
			@RequestParam(required = true) String price,
			@RequestParam(required = true) String userId) throws Exception {
		
		if (StringUtils.isBlank(price))
			return MapResult.failMap();

		String out_trade_no = generateOrderNo();
		// 数据库中创建订单信息
		createOrder(out_trade_no, userId);

		try {

			String orderInfo = AlipayCore.getOrderInfo(body, "订单号："
					+ out_trade_no, price, out_trade_no);
			String sign = AlipayCore.sign(orderInfo, AlipayConfig.private_key);
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");

			String orderString = orderInfo + "&sign=\"" + sign + "\"&"
					+ AlipayCore.getSignType();
			
			Map<String, Object> ret = MapResult.initMap();
			
			Map<String, String> infoMap = new HashMap<String, String>();
			infoMap.put("orderInfo", orderString);
			infoMap.put("orderNo", out_trade_no);
			
			ret.put("result", infoMap);
			return ret;

		} catch (Exception e) {
			return MapResult.failMap();
		}
	}

	/**
	 * 微信支付订单信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWXPayReq")
	public  Map<String, Object> getWXPayReq(@RequestParam(required = true) String body,
			@RequestParam(required = true) String price,
			@RequestParam(required = true) String userId) throws Exception {
		
		if (StringUtils.isBlank(price))
			return MapResult.failMap();

		String out_trade_no = generateOrderNo();

		// 数据库中创建订单信息
		createOrder(out_trade_no, userId);

		String noncestr = PayCommonUtil.CreateNoncestr();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

		SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
		parameters.put("appid", WXPayConfig.APPID);
		parameters.put("mch_id", WXPayConfig.MCH_ID);
		parameters.put("nonce_str", noncestr);
		parameters.put("body", body);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", price);
		parameters.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress());
		parameters.put("notify_url", WXPayConfig.NOTIFY_URL);
		parameters.put("trade_type", "APP");
		String sign = PayCommonUtil.createSign("UTF-8", parameters);
		parameters.put("sign", sign);
		String requestXML = PayCommonUtil.getRequestXml(parameters);
		// 以POST方式调用微信统一支付接口 取得预支付id
		String result = CommonUtil.httpsRequest(WXPayConfig.UNIFIED_ORDER_URL,
				"POST", requestXML);
		// 解析微信返回的信息，以Map形式存储便于取值
		Map<String, String> map = XMLUtil.doXMLParse(result);

		// 获取prepayId
		String prepayid = map.get("prepay_id");
		logger.info("获取prepayid------值 " + prepayid);

		// 吐回给客户端的参数
		if (null != prepayid && !"".equals(prepayid)) {
			SortedMap<Object, Object> params = new TreeMap<Object, Object>();
			params.put("appid", WXPayConfig.APPID);
			params.put("partnerid", WXPayConfig.MCH_ID);
			params.put("prepayid", prepayid);
			params.put("timestamp", timestamp);
			params.put("noncestr", noncestr);
			params.put("package", "Sign=WXPay");
			params.put("sign", PayCommonUtil.createSign("UTF-8", params));

			Map<String, Object> ret = MapResult.initMap();
			ret.put("result", JSONObject.fromObject(params));
			return ret;
		} else {
			return MapResult.failMap();
		}
	}
	
	/**
	 * 支付成功回调
	 */
	private void paySuccessCallBack() {
		System.out.print("success");
	}

	/**
	 * 生成用于支付的订单号
	 */
	private String generateOrderNo() {
		// 当前时间 yyyyMMddHHmmss
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		// 订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行

		return strReq;
	}

	/**
	 * 数据库中生成订单
	 * 
	 * @param orderNo
	 *            订单号
	 * @param expertGid
	 *            专家Gid
	 */
	private void createOrder(String orderNo, String expertGid) {

	}

	
}
