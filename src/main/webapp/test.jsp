<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 	<form action="/user/update" method="post" enctype="multipart/form-data">
		<input type="file" name="avator" id="avator" /> <input type="submit">
	</form> -->


	<!-- <form action="user/update" method="post" enctype="multipart/form-data">
		<input type="file" name="avator" id="avator" /> <br /> <input
			type="submit" name="updateSbt" value="updateSbts" />
	</form> -->
	<form id="firstUpdateForm" action="user/fileUpload" method="post"
		enctype="multipart/form-data" class="form-horizontal" role="form"
		target="hidden_frame">
		<input type="file" name="file">
		<input type="submit" value="提交" />
	</form>
</body>
</html>