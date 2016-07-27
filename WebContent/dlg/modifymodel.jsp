<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>


</style>
<div style="height:100%;width:100%;padding-top:20px;">
	<table id="modifyModelTab" class="dlgtabStyle" cellpadding="10" cellspacing="25">
	<tr>
			<!-- <input type="text" class="form-control" id="name" placeholder="请输入名称"> -->
			<td height="45px" width="25%">运营商:</td><td width="75%"><input type="text" class="form-control" name="corpname"/></td>
		</tr>
		<tr>
			<td height="45px"  width="25%">机顶盒型号:</td><td width="75%"><input type="text" name="modelname" class="form-control"></td>
		</tr>
		<tr>
			<td height="45px">数据服务器:</td><td><input type="text" name="hosturl" class="form-control"></td>
		</tr>
		<tr>
			<td height="45px">软件版本号:</td><td><input type="text" name="version" class="form-control"></td>
		</tr>
	</table>
	<a href="javascript:;" onclick="modifyModelSubmit();" class="dlgbtn">提交</a>
	<span id="ipterr"></span>
</div>
<script>
$(function(){
	$("input[name=corpname]").val(window.corpname);
	$("input[name=hosturl]").val(window.hosturl);
	$("input[name=modelname]").val(window.modelname);
	$("input[name=version]").val(window.modelversion);
	
	$("input").keydown(function(e){
		if(e.keyCode==13){
	    	if(this.name=="modelname"){
	    		$("input[name=hosturl]").focus(); //选中密码输入框
	    	}else if(this.name=="hosturl"){
	    		$("input[name=version]").focus();
	    	}
	    }
	});
	$("input[name=corpname]").focus();
});

function modifyModelSubmit(){
	$("#ipterr").text('');
	var corpname = $("input[name=corpname]").val();
	if(corpname==''){
		$("#ipterr").text('请填写运营商!');
		return;
	}
	var modelname = $("input[name=modelname]").val();
	if(modelname==''){
		$("#ipterr").text('请填写型号!');
		return;
	}
	var hosturl = $("input[name=hosturl]").val();
	if(hosturl==''){
		$("#ipterr").text('请填写数据服务器URL');
		return;
	}
	var version = $("input[name=version]").val();
	if(version==''){
		$("#ipterr").text('请填写版本号');
		return;
	}
	$.post('config',{
		corpname:corpname,
		model:modelname,
		hosturl:hosturl,
		version:version,
		id:window.modelid,
		type:3,
	},function(data,status){
		if(data=='ok'){
			$("#ipterr").text('修改成功!');
			setTimeout(function(){
				closewd();
			},1000);
			keyWordSearch();
		}else if(data=='exist'){
			$("#ipterr").text('ERR:型号已存在!');
		}else if(data=='sqlerr'){
			$("#ipterr").text('ERR:sql出错，请检查数据库连接或联系开发人员!');
		}
	});
}
</script>

