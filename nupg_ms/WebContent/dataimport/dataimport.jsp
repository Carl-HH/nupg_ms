<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<style>
#dataimport_centent{
	position:relative;
	text-align:center;
	margin:120px auto;
}

#model_prompt li{
	list-style:none;
	padding-left:12px;
	cursor:pointer;
}
#model_prompt li:hover{
	background-color:#337ab7;;
}

#model_prompt{
	position:absolute;
	z-index:99;
	border:1px solid #ccc;
	line-height:25px;
	padding:0px;
	background:#fff;
}

.upload_area{
	width:330px;
	margin-left:515px;
	margin-top:30px;
	#border:1px solid #000;
	padding:0px;
}
.upload_btn{
	border:1px solid #ccc;
	height:34px;
	line-height:32px;
	border-radius:5px;
	cursor:pointer;
}

.real_file_ipt{
	position:absolute;
	left:0px;
	top:0px;
	z-index:-1;
	opacity:0;
}
</style>

<div id="dataimport_centent">
	  <div class="col-lg-6" style="width:360px;margin-left:500px;">
         <div class="input-group">
            <input type="text" class="form-control" id ="stb_model">
            <span class="input-group-btn">
               <button class="btn btn-default" type="button">选择型号</button>
            </span>
         </div>
      </div>
      <!-- ------------------------------------------------------------------- -->
      
	      <div class="col-lg-6 upload_area" style="#border:1px solid #000;padding-left:0px;">
	      	<div style="#border:1px solid red;text-align:left;line-height:30px;">
			    <span>上传文件类型：</span>
		    	<input type="radio" name="filetype" value="1" checked="checked"/>报盘文件<span style="display:inline-block;width:15px;"></span>
		    	<input type="radio" name="filetype" value="2"/>型号Data
		    </div>
	      </div>
	      <!-- ------------------------------------------------------------------- -->
	      <div class="col-lg-6 upload_area">
	         <div class="upload_btn">
	         	<span style="font-size:16px;">+</span>选择上传文件
	         </div>
	         <form method="POST" name="uploadForm" action="uploadFile" id="uploadFileForm">
	         <input type="file" name="fileInput" class="real_file_ipt" />
	         <input type="hidden" name="filetype" value=""/>
	         <input type="hidden" name="modelid" value=""/>
	         </form>
	         <div class="file_name" ></div>
	      </div>
      
      <!-- ------------------------------------------------------------------- -->
      <div class="col-lg-6 upload_area" style="margin-top:40px;">
         <button class="btn btn-primary" style="width:100%;" onclick="uploadFileSubmit();">提交</button>
      </div>
      <div class="col-lg-6 upload_area" id="paramErr" style="margin-top:10px;text-align:left;color:red;line-height:30px;"></div>
      
</div>

<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/prompt.js"></script>
<script>
(function(){
	$(".upload_btn").click(function(){
		//$(this).next("input").click();
		$("input[name=fileInput]").click();
	});
	$("input[type=file]").change(function(){
		$(this).next("div.file_name").html($(this).val());
	});
	
	
	$("#uploadFileForm").ajaxForm(function(data){
		if(data=="ok"){
			$("#paramErr").html('<span style="color:green;">操作成功!</span>');
			window.uploading = false;
		}else{
			$("#paramErr").html(data);
		}
		$("input[type=radio][name=filetype]:eq(0)").click();
		$("#stb_model").val("");
		$("input[type=file]").val('');
	});
	
	
})();

function uploadFileSubmit(){
	if(window.uploading){
		$("#paramErr").html("请刷新界面后重试!");
		return;
	}
	$("#paramErr").html("");
	var filetype = $("input[type=radio][name=filetype]:checked").val();
	var modelname = $("#stb_model").val();
	if(modelname==''){
		$("#paramErr").html("请输入型号!");
		return;
	}
	if(!this.models){
		$("#paramErr").html("型号有误!");
		return;
	}
	var tmp = this.models;
	var modelid = 0;
	var tag = tmp.some(function(item,index,arr){
		if(item.model==modelname){
			modelid = item.id;
			return true;
		}
		return false;
	});
	if(!tag){
		$("#paramErr").html("型号不存在!");
		return;
	}
	if($("input[type=file][name=fileInput]").val()==""){
		$("#paramErr").html("请添加文件!");
		return;
	}
	$("#uploadFileForm input[name=filetype]").val(filetype);
	$("#uploadFileForm input[name=modelid]").val(modelid);
	$("#uploadFileForm").submit();
	$("#paramErr").html('<span style="color:green;">数据正在提交中...</span>');
	window.uploading = true;
}

</script>