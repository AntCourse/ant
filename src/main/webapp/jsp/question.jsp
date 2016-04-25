<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" ></c:set>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<script type="text/x-mathjax-config">
      MathJax.Hub.Config({
        messageStyle: "none",
        tex2jax: {inlineMath: [["$","$"],["\\(","\\)"]]}
      });
    </script>

<script type="text/javascript"
	src="${ctx}/js/MathJax.js?config=TeX-AMS_HTML-full"></script>

<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>

<script type="text/javascript">

	var questionList;
	var currentIndex = 0;

	function updateMath(data) {

		var questionDIV = document.getElementById("question");
		questionDIV.className = 'optionLine';
		questionDIV.innerHTML = data.question;
		MathJax.Hub.Queue([ "Typeset", MathJax.Hub, questionDIV ]);
		
		var option1DIV = document.getElementById("option1");
		option1DIV.innerHTML = 'A. '+ data.optionList[0].content;
		MathJax.Hub.Queue([ "Typeset", MathJax.Hub, option1DIV ]);
		
		var option2DIV = document.getElementById("option2");
		option2DIV.innerHTML = 'B. '+ data.optionList[1].content;
		MathJax.Hub.Queue([ "Typeset", MathJax.Hub, option2DIV ]);
		
		var option3DIV = document.getElementById("option3");
		option3DIV.innerHTML = 'C. '+ data.optionList[2].content;
		MathJax.Hub.Queue([ "Typeset", MathJax.Hub, option3DIV ]);
		
		var option4DIV = document.getElementById("option4");
		option4DIV.innerHTML = 'D. '+ data.optionList[3].content;
		MathJax.Hub.Queue([ "Typeset", MathJax.Hub, option4DIV ]);
	}
	
	function clickPrevious(){
		if(currentIndex>0){
			currentIndex--;
			updateMath(questionList[currentIndex]);
		}
	}
	
	function clickNext(){
		if(currentIndex<questionList.length){
			currentIndex++;
			updateMath(questionList[currentIndex]);
		}
	}

	$.ajax({
		type : 'POST',
		url : '${ctx}/cour/getPoiSubList',
		data : {
			"pointId" : "53"
		},
		success : function(data) {
			questionList = data.result;
			updateMath(questionList[currentIndex]);
		},
		dataType : "JSON"
	});
</script>

<style type='text/css'>
.optionLine {
	border-bottom: 5px solid #f0f0f0;
	padding: 10px 2px;
	font-size: 18px;
}

input[type="checkbox"] {
	-webkit-appearance: none;
	height: 24px;
	vertical-align: middle;
	width: 24px;
	border-width: 0px;
}

input[type="checkbox"]:checked {
    background: #fff url(ic_selected.png);
}
input[type="checkbox"]:not(:checked){
	background: #fff url(ic_unselect.png);
}

.footer{  
   position: absolute;  
   bottom: 0;   
   left:0;
   height: 30px; 
   width:100%;
}
.footer #left{
position: relative;
float:left;
left:0px;
}
.footer #right{
position: relative;
float:right;
right:0px; 
}  
</style>
</head>
<body>

	<div id='question'></div>

	<table style="width: 100%; border: 0px">
		<tr>
			<td align='left'><div id='option1' /></td>
			<td align='right'><input type='checkbox' /></td>
		</tr>
		<tr>
			<td colspan='2'><hr color="#f0f0f0" size="5" width="100%" /></td>
		</tr>
		<tr>
			<td align='left'><div id='option2' /></td>
			<td align='right'><input type='checkbox' /></td>
		</tr>
		<tr>
			<td colspan='2'><hr color="#f0f0f0" size="5" width="100%" /></td>
		</tr>
		<tr>
			<td align='left'><div id='option3' /></td>
			<td align='right'><input type='checkbox' /></td>
		</tr>
		<tr>
			<td colspan='2'><hr color="#f0f0f0" size="5" width="100%" /></td>
		</tr>
		<tr>
			<td align='left'><div id='option4' /></td>
			<td align='right'><input type='checkbox' /></td>
		</tr>
		<tr>
			<td colspan='2'><hr color="#f0f0f0" size="5" width="100%" /></td>
		</tr>

	</table>
	
	<br />
	<a onClick="window.wst.startFunction()">点击调用java代码</a>
	<br />
	<a onClick="window.wst.startFunction('hello world')">点击调用java代码并传递参数</a>
	<br />
	
	<div class="footer"><div id="left" onClick='clickPrevious()'>上一题</div><div id="right" onclick="clickNext()">下一题</div></div>

</body>
</html>