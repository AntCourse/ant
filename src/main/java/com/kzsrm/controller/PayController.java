package com.kzsrm.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayCore;
import com.kzsrm.model.Course;
import com.kzsrm.model.WXPayInfo;
import com.kzsrm.utils.MapResult;
import com.tenpay.AccessTokenRequestHandler;
import com.tenpay.ClientRequestHandler;
import com.tenpay.PackageRequestHandler;
import com.tenpay.PrepayIdRequestHandler;
import com.tenpay.util.ConstantUtil;
import com.tenpay.util.TenpayUtil;
import com.tenpay.util.WXUtil;

@Controller
@RequestMapping("/pay")
public class PayController {
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
	
	/**
	 * 微信支付
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getWXPayInfo")
	public Map<String, Object> getWXPayInfo(HttpServletRequest request,HttpServletResponse response) {
		try{

			Map<String, Object> ret = MapResult.initMap();
			
			//接收财付通通知的URL
			String notify_url = "http://127.0.0.1:8180/tenpay_api_b2c/payNotifyUrl.jsp";

			//---------------生成订单号 开始------------------------
			//当前时间 yyyyMMddHHmmss
			String currTime = TenpayUtil.getCurrTime();
			//8位日期
			String strTime = currTime.substring(8, currTime.length());
			//四位随机数
			String strRandom = TenpayUtil.buildRandom(4) + "";
			//10位序列号,可以自行调整。
			String strReq = strTime + strRandom;
			//订单号，此处用时间加随机数生成，商户根据自己情况调整，只要保持全局唯一就行
			String out_trade_no = strReq;
			//---------------生成订单号 结束------------------------

			PackageRequestHandler packageReqHandler = new PackageRequestHandler(request, response);//生成package的请求类 
			PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(request, response);//获取prepayid的请求类
			ClientRequestHandler clientHandler = new ClientRequestHandler(request, response);//返回客户端支付参数的请求类
			packageReqHandler.setKey(ConstantUtil.PARTNER_KEY);

			int retcode ;
			String retmsg = "";
			String xml_body = "";
			//获取token值 
			
			String token = AccessTokenRequestHandler.getAccessToken();
			
			logger.info("获取token------值 " + token);
			
			if (!"".equals(token)) {
				//设置package订单参数
				packageReqHandler.setParameter("bank_type", "WX");//银行渠道
				packageReqHandler.setParameter("body", "测试"); //商品描述   
				packageReqHandler.setParameter("notify_url", notify_url); //接收财付通通知的URL  
				packageReqHandler.setParameter("partner", ConstantUtil.PARTNER); //商户号    
				packageReqHandler.setParameter("out_trade_no", out_trade_no); //商家订单号   
				packageReqHandler.setParameter("total_fee", "1"); //商品金额,以分为单位  
				packageReqHandler.setParameter("spbill_create_ip",request.getRemoteAddr()); //订单生成的机器IP，指用户浏览器端IP  
				packageReqHandler.setParameter("fee_type", "1"); //币种，1人民币   66
				packageReqHandler.setParameter("input_charset", "GBK"); //字符编码

				//获取package包
				String packageValue = packageReqHandler.getRequestURL();
				
				logger.info("获取package------值 " + packageValue);

				String noncestr = WXUtil.getNonceStr();
				String timestamp = WXUtil.getTimeStamp();
				String traceid = "";
				////设置获取prepayid支付参数
				prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
				prepayReqHandler.setParameter("appkey", ConstantUtil.APP_KEY);
				prepayReqHandler.setParameter("noncestr", noncestr);
				prepayReqHandler.setParameter("package", packageValue);
				prepayReqHandler.setParameter("timestamp", timestamp);
				prepayReqHandler.setParameter("traceid", traceid);

				//生成获取预支付签名
				String sign = prepayReqHandler.createSHA1Sign();
				//增加非参与签名的额外参数
				prepayReqHandler.setParameter("app_signature", sign);
				prepayReqHandler.setParameter("sign_method",
						ConstantUtil.SIGN_METHOD);
				String gateUrl = ConstantUtil.GATEURL + token;
				prepayReqHandler.setGateUrl(gateUrl);

				//获取prepayId
				String prepayid = prepayReqHandler.sendPrepay();
				
				logger.info("获取prepayid------值 " + prepayid);
				WXPayInfo wxInfo = new WXPayInfo();
				//吐回给客户端的参数
				if (null != prepayid && !"".equals(prepayid)) {
					//输出参数列表
					clientHandler.setParameter("appid", ConstantUtil.APP_ID);
					clientHandler.setParameter("appkey", ConstantUtil.APP_KEY);
					clientHandler.setParameter("noncestr", noncestr);
					//clientHandler.setParameter("package", "Sign=" + packageValue);
					clientHandler.setParameter("package", "Sign=WXPay");
					clientHandler.setParameter("partnerid", ConstantUtil.PARTNER);
					clientHandler.setParameter("prepayid", prepayid);
					clientHandler.setParameter("timestamp", timestamp);
					//生成签名
					sign = clientHandler.createSHA1Sign();
					clientHandler.setParameter("sign", sign);
					
					wxInfo.setAppId(ConstantUtil.APP_ID);
					wxInfo.setAppkey(ConstantUtil.APP_KEY);
					wxInfo.setNoncestr(noncestr);
					wxInfo.setWxPackage("Sign=" + packageValue);
					//wxInfo.setWxPackage("Sign=WXPay");
					wxInfo.setPartnerId(ConstantUtil.PARTNER);
					wxInfo.setTimestamp(timestamp);
					wxInfo.setPrepayId(prepayid);
					wxInfo.setSign(sign);

					ret.put("result", wxInfo);
					return ret;
				} else {
					retcode = -2;
					retmsg = "错误：获取prepayId失败";
					return  MapResult.initMap(retcode,retmsg);
				}
			} else {
				retcode = -1;
				retmsg = "错误：获取不到Token";
				return  MapResult.initMap(retcode,retmsg);
			}
			
		} catch (Exception e) {
			logger.error("", e);
			return MapResult.failMap();
		}
	}
	
}
