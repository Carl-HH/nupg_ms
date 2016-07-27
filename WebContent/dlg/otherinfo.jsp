<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
#dataManager{
	height:60%;
	width:80%;
	margin:8% auto 20% auto;
	#border:1px solid red;
}
#dataManager table{
	width:100%;
	height:100%;
}
#dataManager table thead tr{
	background:#b0daee;
	border-top:1px solid #aaa;
}
#dataManager table tr{
	border-bottom:1px solid #aaa;
	border-left:1px solid #aaa;
	border-right:1px solid #aaa;
}
#dataManager table td{
	#border:1px solid red;
	text-align:center;
}
.operateLink{
	padding:5px;
}
</style>
<div id="dataManager">
	<table cellpadding="0" cellspacing="10">
		<thead>
			<tr>
				<td width="30%" height="40px">条目</td>
				<td width="20%">状态</td>
				<td width="50%">操作</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>STB数据</td>
				<td class="stbcount">-(台)</td>
				<td>
					<a href="javascript:;" class="operateLink emptySTBData">清空</a>
					<a href="javascript:;" class="operateLink downloadSTBData">下载</a>
				</td>
			</tr>
			<tr>
				<td>型号数据</td>
				<td class="modelstatus">-</td>
				<td>
					<a href="javascript:;" class="operateLink deleteModelData">删除</a>
					<a href="javascript:;" class="operateLink downloadModelData">下载</a>
				</td>
			</tr>
		</tbody>
	</table>
	<div style="margin:10px auto;width:100%;color:red;" id="DataManageInfo"></div>
</div>
<script>

(function(){
	flushDataStatus();
	
	
	
	
})();

(function(){
	//清空机顶盒数据
	$(".emptySTBData").click(function(){
		if(!confirm("确定要清空此型号STB!")){
			return;
		}
		$.post('getModel',{
			modelname:window.dataManager_model,
			type:3,
		},function(data){
			if(data=='ok'){
				setStatus("清空STB数据成功!",true);
				flushDataStatus();
			}else{
				setStatus("清空STB数据失败!",false);
			}
		});
	});
	
	//下载机顶盒数据
	$(".downloadSTBData").click(function(){
		if(!window.haveReport){
			setStatus("没有STB数据可供下载!",false);
			return;
		}
		$.post('getModel',{
			modelname:window.dataManager_model,
			type:4,
		},function(data){
			if(data=='err'){
				setStatus("下载STB数据失败!",false);
			}else{
				location.href = data;
			}
		});
	});
	
	//删除型号Data
	$(".deleteModelData").click(function(){
		if(!confirm("确定要删除型号Data!")){
			return;
		}
		$.post('getModel',{
			id:window.dataManager_id,
			type:5,
		},function(data){
			if(data=='ok'){
				setStatus("删除型号数据成功!",true);
				flushDataStatus();
			}else{
				setStatus("删除型号数据失败!",false);
			}
		});
	});
	
	//下载型号数据
	$(".downloadModelData").click(function(){
		if(window.downloadDataURL=="null"){
			setStatus("没有型号Data可供下载!",false);
			return;
		}
		location.href = window.downloadDataURL;
	});
})();

function flushDataStatus(){
	$.post('getModel',{
		id:window.dataManager_id,
		type:2,
	},function(data){
		if(data=='err'){
			$("#DataManageInfo").html("获取数据失败！");
			return;
		}
		console.log(data);
		var obj = JSON.parse(data);
		$(".stbcount").html(obj.count+'(台)');
		if(obj.count==0){
			window.haveReport = false;
		}else{
			window.haveReport = true;
		}
		if(obj.datastatus==0){
			$(".modelstatus").html('无');
		}else if(obj.datastatus==1){
			$(".modelstatus").html('有');
		}
		window.downloadDataURL = obj.datapath;
		
	});
}

function setStatus(str,tag){
	if(tag){
		$("#DataManageInfo").html('<span style="color:green;">'+str+'</span>');
	}else{
		$("#DataManageInfo").html(str);
	}
}

</script>

