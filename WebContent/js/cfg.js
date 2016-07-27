
$(function(){
	
	getModelTab(1);

});

function getModelTab(curPage,keyword){
	var kwd = '';
	if(arguments.length==2){
		kwd = keyword;
	}
	$.post('config',{
		currentPage:curPage,
		keyword:kwd,
		type:2,
	},function(data,status){
		var obj = JSON.parse(data);
		initModelTab(obj);
	});
}

function geUptTab(curPage,keyword){
	var kwd = '';
	if(arguments.length==2){
		kwd = keyword;
	}
	$.post('config',{
		currentPage:curPage,
		keyword:kwd,
		type:2,
	},function(data,status){
		var obj = JSON.parse(data);
		initUptTab(obj);
	});
}

function initUptTab(obj){
	$("#uptTab tbody").empty();
	if(obj==null){
		$("#uptTab tbody").append($('<tr><td colspan="6" align="center" style="color:red;">没有相关的数据!</td></tr>'));
		return;
	}
	var data = obj.pageList;
	for(var i=0;i<data.length;i++){
		var str =  '<tr><td align="center" height="26px">'+(i+1)+'</td><td align="center">'+data[i].corpname+'</td>'+
					'<td align="center">'+data[i].model+'</td>'+
					'<td align="center">'+data[i].version+'</td><td align="center">'+data[i].sum+'</td>'+
					'<td align="left">'+data[i].hosturl+'</td><td align="center">'+
						'<a href="javascript:;" onclick="uptSetting('+data[i].id+','+data[i].isforce+','+data[i].issnupt+',\''+data[i].snstart+'\',\''+data[i].snend+'\',\''+data[i].model+'\');">升级设置</a>'+
				
					'</td></tr>';
		$("#uptTab tbody").append($(str));
	}
	
	$("#uptTabFoot").empty();
	var htmfoot = '共<span style="font-weight:bold;color:blue;">'+obj.totalSize+'</span>条记录 &nbsp;&nbsp;&nbsp;&nbsp;';
	if(obj.currentPage!=1)
	{
		htmfoot += '<a href="#" onclick="geUptTab('+1+')">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		htmfoot += '<a href="#" onclick="geUptTab('+(obj.currentPage-1)+')">上一页</a>';
	}
	else{
		htmfoot += '首页&nbsp;&nbsp;&nbsp;&nbsp;上一页';
	}
	htmfoot += '&nbsp;&nbsp;&nbsp;&nbsp;'+obj.currentPage+'/'+obj.totalPage+'&nbsp;&nbsp;&nbsp;&nbsp;';
	if(obj.currentPage < obj.totalPage)
	{
		htmfoot += '<a href="#" onclick="geUptTab('+(obj.currentPage+1)+')">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		htmfoot += '<a href="#" onclick="geUptTab('+obj.totalPage+')">尾页</a>';
	}
	else{
		htmfoot += '下一页&nbsp;&nbsp;&nbsp;&nbsp;尾页';
	}
	$("#uptTabFoot").append(htmfoot);
}

function initModelTab(obj){
	$("#modelTab tbody").empty();
	if(obj==null){
		$("#modelTab tbody").append($('<tr><td colspan="6" align="center" style="color:red;">没有相关的数据!</td></tr>'));
		return;
	}
	var data = obj.pageList;
	for(var i=0;i<data.length;i++){
		var str = '<tr><td align="center" height="26px">'+(i+1)+'</td><td align="center">'+data[i].corpname+'</td>'+
					'<td align="center">'+data[i].model+'</td>'+
					'<td align="center">'+data[i].version+'</td><td align="center">'+data[i].sum+'</td>'+
					'<td align="left">'+data[i].hosturl+'</td><td align="center">'+
						'<a href="javascript:;" onclick="modifyModel('+data[i].id+',\''+data[i].corpname+'\',\''+data[i].model+'\',\''+data[i].version+'\',\''+data[i].hosturl+'\');">修改</a>'+
						'<a href="javascript:;" onclick="deleteModel('+data[i].id+',\''+data[i].model+'\')">删除</a>'+
						'<a href="javascript:;" onclick="otherInfo('+data[i].id+',\''+data[i].model+'\')">数据管理</a>'+
					'</td></tr>';
		$("#modelTab tbody").append($(str));
	}
	
	$("#modelTabFoot").empty();
	var htmfoot = '共<span style="font-weight:bold;color:blue;">'+obj.totalSize+'</span>条记录 &nbsp;&nbsp;&nbsp;&nbsp;';
	if(obj.currentPage!=1)
	{
		htmfoot += '<a href="#" onclick="getModelTab('+1+')">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		htmfoot += '<a href="#" onclick="getModelTab('+(obj.currentPage-1)+')">上一页</a>';
	}
	else{
		htmfoot += '首页&nbsp;&nbsp;&nbsp;&nbsp;上一页';
	}
	htmfoot += '&nbsp;&nbsp;&nbsp;&nbsp;'+obj.currentPage+'/'+obj.totalPage+'&nbsp;&nbsp;&nbsp;&nbsp;';
	if(obj.currentPage < obj.totalPage)
	{
		htmfoot += '<a href="#" onclick="getModelTab('+(obj.currentPage+1)+')">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		htmfoot += '<a href="#" onclick="getModelTab('+obj.totalPage+')">尾页</a>';
	}
	else{
		htmfoot += '下一页&nbsp;&nbsp;&nbsp;&nbsp;尾页';
	}
	$("#modelTabFoot").append(htmfoot);
}

function keyWordSearch(){
	var keyWord = $("input[name=search]").val();
	getModelTab(1,keyWord);
}

function uptkeyWordSearch(){
	var keyWord = $("input[name=uptsearch]").val();
	geUptTab(1,keyWord);
}

function modifyModel(id,corpname,model,version,hosturl){
	window.corpname = corpname;
	window.modelid = id;
	window.modelname = model;
	window.modelversion = version;
	window.hosturl = hosturl;
	var htmlwrap = '<div id="wrapLayer"></div>';
	$("body").prepend($(htmlwrap));
	var wndStr = '<div id="wnd"><div id="wnd_banner"><span></span><a href="#" onclick="closewd()"><img src="images/close.png"></a></div><div id="wnd_content"></div></div>';
	$("body").prepend($(wndStr));
	$("#wnd").width(450).height(360);
	var ntop = ($("#wrapLayer").height()-$("#wnd").height())/2;
	var nleft = ($("#wrapLayer").width()-$("#wnd").width())/2;
	$("#wnd").css("marginLeft",nleft).css("top",ntop);
	$("#wnd_banner span").text("修改型号");
	$("#wrapLayer").css("opacity",0.65);			//为了使opacity属性在IE上生效；
	$("#wrapLayer").show();
	$("#wnd_content").load("dlg/modifymodel.jsp");
}

function deleteModel(id,model){
	
	if(!confirm("确定要删除型号\""+model+"\"?")){
		return;
	}
	$.post('config',{
		type:4,
		id:id,
	},function(data,status){
		if(data=='ok'){
			alert('删除成功!');
			keyWordSearch();
		}else if(data=='sqlerr'){
			alert('删除失败!');
		}
	});
}

function addModel(){
	var htmlwrap = '<div id="wrapLayer"></div>';
	$("body").prepend($(htmlwrap));
	var wndStr = '<div id="wnd"><div id="wnd_banner"><span></span><a href="#" onclick="closewd()"><img src="images/close.png"></a></div><div id="wnd_content"></div></div>';
	$("body").prepend($(wndStr));
	$("#wnd").width(450).height(360);
	var ntop = ($("#wrapLayer").height()-$("#wnd").height())/2;
	var nleft = ($("#wrapLayer").width()-$("#wnd").width())/2;
	$("#wnd").css("marginLeft",nleft).css("top",ntop);
	$("#wnd_banner span").text("添加型号");
	$("#wrapLayer").css("opacity",0.65);			//为了使opacity属性在IE上生效；
	$("#wrapLayer").show();
	$("#wnd_content").load("dlg/addmodel.jsp");
}
function closewd()   //关闭弹出框
{
	$("#wrapLayer").remove();//css("display","none");
	$("#wnd").remove();
}

function otherInfo(id,model){
	var htmlwrap = '<div id="wrapLayer"></div>';
	$("body").prepend($(htmlwrap));
	var wndStr = '<div id="wnd"><div id="wnd_banner"><span></span><a href="#" onclick="closewd()"><img src="images/close.png"></a></div><div id="wnd_content"></div></div>';
	$("body").prepend($(wndStr));
	$("#wnd").width(450).height(320);
	var ntop = ($("#wrapLayer").height()-$("#wnd").height())/2;
	var nleft = ($("#wrapLayer").width()-$("#wnd").width())/2;
	$("#wnd").css("marginLeft",nleft).css("top",ntop);
	$("#wnd_banner span").text('数据管理('+model+')');
	$("#wrapLayer").css("opacity",0.65);			//为了使opacity属性在IE上生效；
	$("#wrapLayer").show();
	$("#wnd_content").load("dlg/otherinfo.jsp");
	window.dataManager_id = id;
	window.dataManager_model = model;
}

function uptSetting(id,isforce,issnupt,snstart,snend,model) {
	window.modelid = id;
	window.isforceid = isforce;
	window.issnupt = issnupt;
	window.snstart = snstart;
	window.snend = snend;
	var htmlwrap = '<div id="wrapLayer"></div>';
	$("body").prepend($(htmlwrap));
	var wndStr = '<div id="wnd"><div id="wnd_banner"><span></span><a href="#" onclick="closewd()"><img src="images/close.png"></a></div><div id="wnd_content"></div></div>';
	$("body").prepend($(wndStr));
	$("#wnd").width(450).height(340);
	var ntop = ($("#wrapLayer").height()-$("#wnd").height())/2;
	var nleft = ($("#wrapLayer").width()-$("#wnd").width())/2;
	$("#wnd").css("marginLeft",nleft).css("top",ntop);
	$("#wnd_banner span").text('升级配置('+model+')');
	$("#wrapLayer").css("opacity",0.65);			//为了使opacity属性在IE上生效；
	$("#wrapLayer").show();
	$("#wnd_content").load("uptcontrol/uptSetting.jsp");
	window.dataManager_id = id;
	window.dataManager_model = model;
}

