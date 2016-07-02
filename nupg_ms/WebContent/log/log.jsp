<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
#logcentent{
	#border:1px solid #000;
	padding-top:50px;
}
.form-control{
	font-size:12px;
}
</style>

<div id="logcentent">

	<div style="width:80%;margin:0px auto;height:26px;line-height:26px;">
		<div class="col-lg-6" style="width:350px;padding-left:0px;">
            <div class="input-group" >
               <input type="text" class="form-control" name="log_search" placeholder="根据日志内容模糊查询">
               <span class="input-group-btn">
                  <button class="btn btn-default" type="button" onclick="logSearch();">查询</button>
               </span>
            </div>
         </div>
		<div id="logTabFoot" style="float:right;width:460px;height:100%;text-align:right;padding-right:5px;"></div>
	</div>
	<table class="table table-hover" style="border:1px solid #ddd;width:80%;margin:10px auto;">
		<thead>
			<tr style="background:#d9edf7;">
				<td width="5%" align="center" height="30px">序号</td>
				<td width="15%" align="center">时间</td>
				<td width="80%" align="center">日志内容</td>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>

</div>

<script>

function logSearch(){
	var keyWord = $("input[name=log_search]").val();
	flushLogList(1,keyWord);
}


(function(){
	flushLogList(1);
})();

function flushLogList(curPage,keyword){
	var kwd = '';
	if(arguments.length==2){
		kwd = keyword;
	}
	$.post('getLogList',{
		currentPage:curPage,
		keyword:kwd,

	},function(data,status){
		//console.log(data);
		var obj = JSON.parse(data);
		initLogTab(obj);
	});
}

function initLogTab(obj){
	$("#logcentent table tbody").empty();
	var data = obj.pageList;
	var reg = /[^.]*/;
	var arr = [];
	for(var i=0;i<data.length;i++){
		arr = reg.exec(data[i].datetime);
		var str = '<tr><td align="center" height="26px">'+(i+1)+'</td><td align="left">'+arr[0]+'</td>'+
					'<td align="left">'+data[i].logcontent+'</td></tr>';
		$("#logcentent table tbody").append($(str));
	}
	
	$("#logTabFoot").empty();
	var htmfoot = '共<span style="font-weight:bold;color:blue;">'+obj.totalSize+'</span>条记录 &nbsp;&nbsp;&nbsp;&nbsp;';
	if(obj.currentPage!=1)
	{
		htmfoot += '<a href="#" onclick="flushLogList('+1+')">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		htmfoot += '<a href="#" onclick="flushLogList('+(obj.currentPage-1)+')">上一页</a>';
	}
	else{
		htmfoot += '首页&nbsp;&nbsp;&nbsp;&nbsp;上一页';
	}
	htmfoot += '&nbsp;&nbsp;&nbsp;&nbsp;'+obj.currentPage+'/'+obj.totalPage+'&nbsp;&nbsp;&nbsp;&nbsp;';
	if(obj.currentPage < obj.totalPage)
	{
		htmfoot += '<a href="#" onclick="flushLogList('+(obj.currentPage+1)+')">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;';
		htmfoot += '<a href="#" onclick="flushLogList('+obj.totalPage+')">尾页</a>';
	}
	else{
		htmfoot += '下一页&nbsp;&nbsp;&nbsp;&nbsp;尾页';
	}
	$("#logTabFoot").append(htmfoot);
}

</script>