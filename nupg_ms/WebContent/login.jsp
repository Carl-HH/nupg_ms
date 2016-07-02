<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>NUPG</title>

<style>
*{
	margin:0px;
	padding:0px;
}

body{
	background:url(images/bgpic.png) no-repeat;
	font-family:Aril;
}
.bgpic_second{
	background:url(images/bg_second.png) no-repeat;
	position:absolute;
	top:10%;
	left:10%;
	width:80%;
	height:80%;
	z-index:-1;
	#border:1px solid #fff;
}
.bgpic_second .logo{
	margin:30px auto auto 30px;
}
.bgpic_second .title{
	margin:130px auto auto 140px;;
	color:#fff;
	font-size:35px;	
	text-shadow:3px 3px 5px black;
	font-family:Aril,微软雅黑;
}
a{
	text-decoration:none;
	color:#fff;
}
#loginTab{
	#border:3px solid #2875a8;
	width:260px;
	height:280px;
	margin:180px 350px 0px auto;
	text-align:center;
	border-radius:5px;
	background:#0f3555;
	#border:5px solid #999;
}
#banner{
	border-bottom:1px solid #3189c1;
	height:40px;
	line-height:40px;
	font-size:20px;
	color:#fff;
}
.iptbg{
	display:block;
	height:26px;
	padding:4px 0px 4px 0px;
	width:220px;
	background:#fff;
	margin:30px auto 10px auto;
	border-radius:5px;
	border:1px solid #999;
}

.logipt{
	margin:0px;
	padding:0px;
	display:block;
	height:26px;
	width:220px;
	text-align:center;
	border-radius:5px;
	font-weight:600;
}

.ipts{
	border:0;
	background: transparent;
    outline:0;
    position:absolute;
    z-index:5;
}

.iptHidden{
	border:0;
	background: transparent;
    outline:0;
    position:absolute;
    z-index:2;
    text-align:left;
	font-weight:400;
	color:#999;
	padding-left:5px;
}

.logbtn{
	background:#398ee7;
	height:33px;
	line-height:33px;
	font-weight:400;
	margin:15px auto 10px auto;
}

#errinfo{
	width:220px;
	height:20px;
	line-height:20px;
	font-size:12px;
	text-align:left;
	color:red;
	margin:0px auto;
	#border:1px solid #000;
}
#registRemind{
	width:220px;
	height:20px;
	line-height:20px;
	margin:5px auto;
	font-size:12px;
	text-align:left;
}
a.normalLink{
	margin-left:2px;
	color:blue;
	text-decoration:underline;
}
.loginBtn{
	margin-top:35px;
}
</style>

<!--[if lt IE 9]>
<script>
alert("请使用Google Chrome浏览器!");
</script>
<![endif]-->

</head>
<body>
	<div class="bgpic_second">
		<div class="logo"><img src="images/logo.png"/></div>
		<div class="title">网络升级管理系统</div>
	</div>
	<div id="loginTab">
		<div id="banner">
			<span >系统登录</span>
		</div>
		<span class="iptbg"><input class="logipt ipts" type="text" name="username"/></span>
		<span class="iptbg"><input class="logipt ipts" type="password" name="pwd"/></span>	
		<a class="logipt logbtn loginBtn" href="#" onclick="submitLogin();">登录</a>
		<div id="errinfo"></div>
	</div>
	
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script>
$(function(){
	$("input").keydown(function(e){
		if(e.keyCode==13){
	    	if(this.name=="username"){
	    		$("input[name=pwd]").focus(); //选中密码输入框
	    	}else if(this.name=="pwd"){
	    		submitLogin();
	    	}
	    }
	});
	$("input[name=username]").focus();
});

function submitLogin(){
	var ipt = document.getElementsByClassName("logipt");
	var usrname = ipt[0].value;
	if(usrname==''){
		$("#errinfo").text("用户名不能为空");
		return;
	}
	var pwd = ipt[1].value;
	if(pwd==''){
		$("#errinfo").text("密码不能为空");
		return;
	}
	//location.href = 'cfg.jsp';
	$.post("login",{
		username:usrname,
		pwd:pwd,
		type:'login',
	},function(data,status){
		if(data=="ok"){
			location.href = 'mainPage.jsp';
		}else if(data=="err"){
			$("#errinfo").text("用户名或密码错误!");
		}
	});
}

</script>
</body>
</html>