<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html">
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>网络升级管理系统</title>
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">


<style>
body{
	padding:0px;
	margin:0px;
}

.content{
	display:none;
}

</style>
</head>
<body>
	<div class="tabbable" style="#background-color:#000;">  
	    <ul class="nav nav-tabs" id="nav_link" style="padding-left:10%;">  
	        <li class="active"><a href="#" data-toggle="tab">Home</a></li>  
	        <li><a href="#" data-toggle="tab">配置型号</a></li>
	        <li><a href="#" data-toggle="tab">数据导入</a></li>
	        <li><a href="#" data-toggle="tab">升级控制</a></li>
	        <li><a href="#" data-toggle="tab">日志</a></li>  
	    </ul>  
	</div>  
	
	
	<div class="content" style="display:block;text-align:center;margin:120px auto;font-size:20px;">
		欢迎使用网络升级管理系统!
	</div>
	
	<div class="content" style="margin:50px auto;">
		<div style="width:76%;margin:0px auto;height:26px;line-height:26px;">
			<div class="col-lg-6" style="width:350px;padding-left:0px;">
	            <div class="input-group" >
	               <input type="text" class="form-control" name="search" placeholder="根据型号和数据服务器URL模糊查询">
	               <span class="input-group-btn">
	                  <button class="btn btn-default" type="button" onclick="keyWordSearch();">查询</button>
	               </span>
	            </div>
	         </div>
	         <button class="btn btn-default" type="button" onclick="addModel();">添加型号</button>
			<div id="modelTabFoot" style="float:right;width:460px;height:100%;text-align:right;padding-right:5px;"></div>
		</div>
		<table id="modelTab" class="table table-hover" style="border:1px solid #ddd;">
		<thead>
			<tr class="info">
				<td align="center" width="10%" height="30px">编号</td>
				<td align="center" width="10%">运营商</td>
				<td align="center" width="10%">型号</td>
				<td align="center" width="5%">版本</td>
				<td align="center" width="10%">数量</td>
				<td align="center" width="35%">数据服务器URL</td>
				<td align="center" width="30%">操作</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	</div>
	<div class="content"></div>
	<div class="content"></div>
	<div class="content"></div>
	
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/cfg.js"></script>
<script>
(function(){
	$("#nav_link li").click(function(){
		var index = $(this).index();
		$(".content").hide();
		$(".content:eq("+index+")").show();
	});
	$(".content:eq(2)").load('dataimport/dataimport.jsp');
	$(".content:eq(3)").load('uptcontrol/upttab.jsp');
	$(".content:eq(4)").load('log/log.jsp');
	
	
	window.addEventListener("err",function(){
		alert("JavaScript出错！");
	},false);
	
	
})();
</script>
</body>
</html>



