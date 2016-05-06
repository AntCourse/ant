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

<style type='text/css'>
.optionLine {
	border-bottom: 5px solid #f0f0f0;
	padding: 10px 2px;
	font-size: 18px;
}

input[type="radio"] {
	-webkit-appearance: none;
	height: 24px;
	vertical-align: middle;
	width: 24px;
	border-width: 0px;
}

input[type="radio"]:checked {
    background: #fff url(ic_selected.png);
}
input[type="radio"]:not(:checked){
	background: #fff url(ic_unselect.png);
}

.button {
	display: inline-block;
	width: 100%;
	outline: none;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	font: 14px/100% Arial, Helvetica, sans-serif;
	padding: .5em 2em .55em;
	text-shadow: 0 1px 1px rgba(0,0,0,.3);
	-webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
	border-radius: .5em;
	-webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	-moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);
	box-shadow: 0 1px 2px rgba(0,0,0,.2);
}
.button:hover {
	text-decoration: none;
}
.button:active {
	position: relative;
	top: 1px;
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

<script type="text/javascript">

	var courseId = <%=request.getParameter("courseId")%>;
	
	var questionList;
	var currentIndex = 0;
	var answerArray;

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
		
		//答案
        var answerDIV = document.getElementById('answer');
        answerDIV.innerHTML = '正确答案：D';
     
        //解析
        var analysisDIV = document.getElementById('analysis');
        analysisDIV.innerHTML = data.hint;
        MathJax.Hub.Queue(["Typeset",MathJax.Hub,analysisDIV]);
	}
	//上一题目
	function clickPrevious(){
		document.getElementById("right").innerHTML = '下一题';
		if(currentIndex>0){
			if(currentIndex==1){
				document.getElementById("left").style.display="none";
			}
			currentIndex--;
			updateMath(questionList[currentIndex]);
		}
	}
	//下一题
	function clickNext(){
		document.getElementById("left").style.display="";
		//选中的答案
		var optionIndex=$('input:radio[name="radio"]:checked').val();
		
		document.getElementById("showBtn").style.display="";
		document.getElementById("answer").style.display="none";
		document.getElementById("analysis").style.display="none";
		if(currentIndex<questionList.length-1){
			var optionId = optionIndex!=null?questionList[currentIndex].optionList[optionIndex].id:'';
			var answerObj = new Object();
			answerObj.questionId = questionList[currentIndex].id;
			answerObj.optionId = optionId;
			answerArray[currentIndex] = answerObj;
			
			if(currentIndex==questionList.length-2){
				document.getElementById("right").innerHTML = '提交';
			}
			currentIndex++;
			updateMath(questionList[currentIndex]);
		}else{
			alert(answerArray);
		}
		//取消选中
		$("[name='radio']").removeAttr("checked");
	}
	//显示答案
	function showAnswer(){
		document.getElementById("showBtn").style.display="none";
		document.getElementById("answer").style.display="";
		document.getElementById("analysis").style.display="";
	}

	$.ajax({
		type : 'POST',
		url : '${ctx}/cour/getPoiSubList',
		data : {
			"pointId" : courseId
		},
		success : function(data) {
			questionList = data.result;
			answerArray = new Array(questionList.length);
			updateMath(questionList[currentIndex]);
		},
		dataType : "JSON"
	});
</script>

</head>
<body>

	<div id='question'></div>

	<table style="width: 100%; border: 0px">
		<tr>
			<td align='left'><div id='option1' /></td>
			<td align='right'><input type='radio' name='radio' value='0'/></td>
		</tr>
		<tr>
			<td colspan='2'><hr color="#f0f0f0" size="5" width="100%" /></td>
		</tr>
		<tr>
			<td align='left'><div id='option2' /></td>
			<td align='right'><input type='radio' name='radio' value='1'/></td>
		</tr>
		<tr>
			<td colspan='2'><hr color="#f0f0f0" size="5" width="100%" /></td>
		</tr>
		<tr>
			<td align='left'><div id='option3' /></td>
			<td align='right'><input type='radio' name='radio' value='2'/></td>
		</tr>
		<tr>
			<td colspan='2'><hr color="#f0f0f0" size="5" width="100%" /></td>
		</tr>
		<tr>
			<td align='left'><div id='option4' /></td>
			<td align='right'><input type='radio' name='radio' value='3'/></td>
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
	
	<div>
        <button id="showBtn" class="button" onclick="showAnswer()">显示答案及解析</button>
        <div id='answer' style="display: none"></div>
        <div>解析</div>
        <div id='analysis' style="display: none"></div>
    </div>
	
	<div class="footer"><div id="left" style="display: none" onClick='clickPrevious()'>上一题</div><div id="right" onclick="clickNext()">下一题</div></div>

</body>
</html>