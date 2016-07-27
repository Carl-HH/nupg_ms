
(function(){
	$("#stb_model").keyup(function(event){
		if(event.keyCode > 40 || event.keyCode == 8 || event.keyCode ==32) { 
			//console.log($(this).val());
			search($(this).val());
		}
	}); 
	
	$("#stb_model").focus(function(){
		if($(this).val()!=''){
			search($(this).val());
		}
	});
	
	//在非提示框和输入框的地方点击时让提示框消失
	$(document).click(function(e){
		if($("#model_prompt").length>0){
			var ipt_left = $("#stb_model").offset().left;
			var reg = /[0-9]*/;
			var padding_left = $("#stb_model").css("paddingLeft");
			var arr =  reg.exec(padding_left);
			var w_len = $("#stb_model").width()+Number(arr[0])*2;
			var ipt_right = ipt_left + w_len;
			if((e.clientX>ipt_left) && (e.clientX<ipt_right)){
				var deadline_top = $("#stb_model").offset().top;
				var prompt_top = $("#model_prompt").offset().top;
				var prompt_height = $("#model_prompt").height();
				var deadline_bottom = prompt_top+prompt_height;
				if(!((e.clientY>deadline_top)&&(e.clientY<deadline_bottom))){
					disappearPromptDlg();
				}
			}else{
				disappearPromptDlg();
			}
		}
	});
	
	
})();

//ajax查询关键字匹配的型号
function search(keyword){
	$.post('getModel',{
		keyword:keyword,
		type:1,
	},function(data){
		//console.log("data:"+data);
		if(data=='[]'){
			addContentToPromptDlg('<li>没有记录</li>');
		}else{
			window.models = eval(data);
			var str = '';
			var len = window.models.length;
			for(var i=0;i<len;i++){
				str += '<li id="'+models[i].id+'">'+models[i].model+'</li>';
			}
			addContentToPromptDlg(str);
			$("#model_prompt li").click(function(){
				$("#stb_model").val($(this).text());
				disappearPromptDlg();
			});
		}
	});
}

function promptDialog(){
	if($("#model_prompt").length==0){
		var htmlstr = '<ul id="model_prompt"></ul>';
		$('body').append($(htmlstr));
	}
	var reg = /[0-9]*/;
	var padding_top = $("#stb_model").css("paddingTop");
	var padding_left = $("#stb_model").css("paddingLeft");
	var arr = reg.exec(padding_top);
	var h_len = $("#stb_model").height()+Number(arr[0])*2;
	arr = reg.exec(padding_left);
	var w_len = $("#stb_model").width()+Number(arr[0])*2;
	var top = $("#stb_model").offset().top+h_len;
	var left = $("#stb_model").offset().left;
	$("#model_prompt").width(w_len);
	$("#model_prompt").css('top',top+2);
	$("#model_prompt").css('left',left);
	$("#model_prompt").empty();
}

function addContentToPromptDlg(str){
	if($("#model_prompt").length==0){
		promptDialog();
	}
	$("#model_prompt").html($(str));
}

function disappearPromptDlg(){
	$("#model_prompt").remove();
}

