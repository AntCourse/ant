<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" ></c:set>
<!DOCTYPE html>
<html lang="zh-CN" class="default-layout">
<head>
<title>欧拉学院 - 最直接的学习方式</title>
<link rel="icon" type="image/vnd.microsoft.icon"
	href="${ctx}/images/icon.png">
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge, chrome = 1">
<meta name="description"
	content="欧拉学院 - 最直接的学习方式">
<link rel="stylesheet"
	href="${ctx}/css/bonjour.css">
</head>
<body data-route="/" data-page-name="Bonjour"
	data-environment="production">
	<div class="wrap">
		<div class="header">
			<div class="logo">
				<img src="${ctx}/images/logo.png" alt="欧拉学院">
			</div>
		</div>
		<div class="main">
			<div class="main-content">
				<div class="left">
					<img src="${ctx}/images/mobile.png" alt="欧拉学院"
						class="mobile">
				</div>
				<div class="right">
					<div class="subtitle"> 最直接的学习方式
					</div>
					<div class="download-wrap">
						<div class="barcode">
							<img src="${ctx}/images/code.png"
								alt="二维码下载">
						</div>
						<div class="download-list">
							<a target="_blank" href="http://www.pgyer.com/swiftacademy"
								onclick="logDownload(&quot;android&quot;)" rel="Android 客户端"
								class="download-item false">Android客户端</a><a target="_blank"
								href="http://www.pgyer.com/swiftacademy"
								onclick="logDownload(&quot;iphone&quot;)" rel="iPhone 客户端"
								class="download-item false">iPhone客户端</a>
						</div>
					</div>
					<div class="teacher-wrap"><a target="_blank" href="mailto:teacher@olaxueyuan.com?subject=入驻申请"
							class="teacher-site-link"><b>&nbsp&nbsp我是老师</b> 我想入驻欧拉学院 &gt;&nbsp&nbsp</a>
					</div>
				</div>
			</div>
		</div>
		<div class="footer">
			<div class="footer-content">
				<p class="text-right">
					<a target="_blank" nofollow href="http://weibo.com/5486296075/profile?topnav=1&wvr=6&is_all=1"
						class="text-primary">关注我们</a><span class="split">&nbsp;|&nbsp;</span><a target="_blank" nofollow href="mailto:contact@olaxueyuan.com"
						class="text-primary">联系我们</a><span class="split">&nbsp;|&nbsp;</span><a target="_blank" nofollow href="mailto:service@olaxueyuan.com"
						class="text-primary">反馈建议</a><span class="split">&nbsp;|&nbsp;</span><a target="_blank" nofollow
						href="http://www.miibeian.gov.cn/">京ICP备15065431号-1,&nbsp</a><span>Michen(Beijing) Technology Co.,Ltd.
						All Rights 幂辰（北京）科技有限公司 版权所有</span>
				</p>
			</div>
		</div>
	</div>
</body>
</html>