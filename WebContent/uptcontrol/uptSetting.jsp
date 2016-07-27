<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">


<div id="uptSetting">
  <div style="height:100%;width:100%;padding-top:20px;">
	<table id="uptSettingTab" class="dlgtabStyle" cellpadding="10" cellspacing="25">
		<tr>
			<td height="45px"  width="50%">强制升级<input type="checkbox" name="forceupt" class="checkbox-control"></td>

			<td height="45px"  width="50%">升级控制<input type="checkbox" name="issnupt" class="checkbox-control"></td>
		</tr>
		<tr>
		</table>
		<table id="uptSNSettingTab" class="dlgtabStyle" cellpadding="10" cellspacing="25">
			<td height="45px"  width="25%">SN开始：</td><td width="75%"><input type="text" name="snstart" class="form-control"></td>
		</tr>
		<tr>
			<td height="45px"  width="25%">SN结束：</td><td width="75%"><input type="text" name="snend" class="form-control"></td>
		</tr>
	</table>
	<a href="javascript:;" onclick="forceuptSubmit();" class="dlgbtn">提交</a>
	<span id="ipterr"></span>
</div>
</div>
<script>
$(function(){
	var isforce = window.isforceid;
	if(1 == isforce){
		$("input[name=forceupt]").attr('checked','checked');
	}
	var issnupt = window.issnupt;
	if(1 == issnupt){
		$("input[name=issnupt]").attr('checked','checked');
	}
	if('null' != window.snstart) {
		$("input[name=snstart]").val(window.snstart);
	}
	if('null' != window.snend ) {
		$("input[name=snend]").val(window.snend);
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
	var issnuptchecked = 0;
	//是否设置为强制升级模式
	var ischecked =document.getElementsByName("forceupt");
	if(ischecked[0].checked) {
		isforce = 1;
	} else {
		isforce = 0;
	}
	
	//设置升级控制模式，
	var issnupt = document.getElementsByName("issnupt");
    if (issnupt[0].checked) {
		issnuptchecked = 1;
	} else {
		issnuptchecked = 0;
	}
    
    var snstart = $("input[name=snstart]").val();
    var snend = $("input[name=snend]").val();
	
    if(snstart == '' && snend =='') {
    	snstart = "null";
    	snend = "null";
    }
    
 	$.post('config',{
 		isforce:isforce,
 		issnupt:issnuptchecked,
 		snstart:snstart,
 		snend:snend,
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

