<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<div id="uptSetting">
  <div style="height:100%;width:100%;padding-top:20px;">
	<table id="uptSettingTab" class="dlgtabStyle" cellpadding="10" cellspacing="25">
		<tr>
			<td height="45px"  width="25%">强制升级</td><td width="75%"><input type="checkbox" name="forceupt" class="form-control"></td>
		</tr>
	
	</table>
	<a href="javascript:;" onclick="forceuptSubmit();" class="dlgbtn">提交</a>
	<span id="ipterr"></span>
</div>
</div>
<script>
$(function(){
	var isforce = window.isforceid;
	if(isforce == 1){
		
		$("input[name=forceupt]").attr('checked','checked')
	}
	/* $("input[name=hosturl]").val(window.hosturl);
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
	$("input[name=modelname]").focus(); */
});

function forceuptSubmit(){
	var isforce = 0;
	var ischecked =document.getElementsByName("forceupt");
	if(ischecked[0].checked) {
		isforce = 1;
	}else{
		isforce = 0;
	}
 	$.post('config',{
 		isforce:isforce,
		id:window.modelid, 
		type:5,
	},function(data,status){
		if(data=='ok'){
			$("#ipterr").text('设置成功!');
			setTimeout(function(){
				closewd();
			},1000);
			uptkeyWordSearch();
		}else {
			$("#ipterr").text('设置失败!');
		}
	}); 
}
</script>

