<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<body>
	<form id="firstUpdateForm" action="user/fileUpload" method="post"
		enctype="multipart/form-data" class="form-horizontal" role="form"
		target="hidden_frame">
		<input type="file" name="file"> <input type="submit"
			value="提交" />
	</form>


	<div>----------------------------------------</div>


	<form action="user/login" method="post">
		<ul>
			<li><label>用户名</label><input name="phone" type="text" /></li>
			<li />
			<li><label>密 码</label><input name="passwd" type="text" /></li>
			<li />
			<li><input type="submit" value="登录" /></li>
		</ul>
	</form>
</body>
</html>