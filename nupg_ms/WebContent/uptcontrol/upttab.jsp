<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
#upttabdiv{
	#border:1px solid #000;
	padding-top:50px;
}
.form-control{
	font-size:12px;
}
</style>

<div id="upttabdiv">
<div style="width:76%;margin:0px auto;height:26px;line-height:26px;">
			<div class="col-lg-6" style="width:350px;padding-left:0px;">
	            <div class="input-group" >
	               <input type="text" class="form-control" name="uptsearch" placeholder="根据型号和数据服务器URL模糊查询">
	               <span class="input-group-btn">
	                  <button class="btn btn-default" type="button" onclick="uptkeyWordSearch();">查询</button>
	               </span>
	            </div>
	         </div>
	      
			<div id="uptTabFoot" style="float:right;width:460px;height:100%;text-align:right;padding-right:5px;"></div>
		</div>
		<table id="uptTab" class="table table-hover" style="border:1px solid #ddd;">
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
	<script type="text/javascript" src="js/cfg.js"></script>
<script>
    $(function(){
    	geUptTab(1);
    })
</script>