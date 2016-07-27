<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
	<span style="margin-left: 100px;">型号：<select id="countrysel" name="contrysel" class="iptselect">

	</select></span>
	
	<div id="canvasDiv"></div>
	<div id="content_footer"></div>
</div>

<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
<script>
 	var versionarr = new Array();
	var valuearr = new Array();
	var modelarr = new Array();
	var colors = [ '#cc6699', '#cbab4f', '#76a871', '#9f7961', '#a56f8f',
			'#6f83a5' ];

	function drawCountChart(valuearr, namearr, page) {
		var data = [];
		var maxValue;
		var scale;
		maxValue = getMaxvalue(valuearr);
		scale = getscalespace(maxValue, 10);
	
		console.log("maxvalue:"+maxValue+" scale:"+scale);
		
		for (var i = 0; i < 6; i++) {
		
			if (i + 6 * (page - 1)< namearr.length) {
				data.push({
					name : versionarr[i + 6 * (page - 1)],
					value : valuearr[i + 6 * (page - 1)],
					color : colors[i % 6],
				});
			}
		}
		
		//柱形图
		new iChart.Column2D({
			render : 'canvasDiv',
			data: data,
			title : '流量管理',
			decimalsnum : 0,
			width : 1200,
			height : 500,
			border : '#fff',
			color : '#ff0000',
			coordinate:{
				background_color:null,
				scale:[{
					 position:'left',	
					 start_scale:0,
					 end_scale:maxValue,
					 scale_space:scale,
					 listeners:{
						parseText:function(t,x,y){
							return {text:t}
						}
					}
				}]
			}
		}).draw();
	/* 条形图
	new iChart.Bar2D({
			render : 'canvasDiv',
			data : data,
			title : '流量统计',
			background_color : null,
			decimalsnum : 0,
			border : '#fff',
			color : '#ff0000',
			width : 1200,
			height : 500,
			coordinate : {
				background_color : null,
				scale : [{
					position : 'bottom',
					start_scale : 0,
					end_scale :maxValue,
					scale_space : scale,
					listeners : {
						parseText : function(t, x, y) {
							return {
								text : t
							}
						}
					}
				}]
			}
		}).draw(); */
		return data;
	}
	
	$(function(){
		$.post('getAllModel',{
		},function(data,status){
			var modelStr = jQuery.parseJSON(data);
			if(data==("null")){
				alert("don't have the data");
			} else {
			    for(var i = 0;i< modelStr.length; i++) {
					 var str = '<option value="'+modelStr[i].model+'">' + modelStr[i].model
					+ '</option>';
					console.log(str);
					 $('#countrysel').append($(str)); 
			    }
			  
			}
			$('#countrysel').change(function(){ 
				alert( $('#countrysel').val());
				var str = $('#countrysel').val();
				$.post('getFlow',{
					type:1,
					model:str,
					currentPage:1,
				},function(data,status){
					var countStr = jQuery.parseJSON(data);
					if(data==("null")){
						alert("don't have the data");
						$('#canvasDiv').empty();
					} else {
						valuearr = [];
						versionarr = [];
						console.log(data);
						for (var i = 0; i < countStr.pageList.length; i++) {
							valuearr.push(countStr.pageList[i].count);
							if(countStr.pageList[i].version != null) {
								versionarr.push(countStr.pageList[i].version);
							} else {
								versionarr.push('none');
							}
						}
						drawCountChart(valuearr,versionarr,1);
					}
				});
			});
			
		});
	})

/*  $(function(){
	$.post('getFlow',{
		type:1,
		model:'6828',
		currentPage:1,
	},function(data,status){
		var countStr = jQuery.parseJSON(data);
		if(data==("null")){
			alert("don't have the data");
		} else {
			valuearr = [];
			versionarr = [];
			console.log(data);
			for (var i = 0; i < countStr.pageList.length; i++) {
				valuearr.push(countStr.pageList[i].count);
				if(countStr.pageList[i].version != null) {
					versionarr.push(countStr.pageList[i].version);
				} else {
					versionarr.push('none');
				}
			}
			drawCountChart(valuearr,versionarr,1);
		}
	});
});   */

/*
get the maxvalue of data.when the data less than 6,set the maxvalue to 5;
*/
function getMaxvalue(valuearr) {
	
	var max_Num = Math.max.apply(null, valuearr);
	
	if(max_Num < 6)
		return 5;
	
	else return max_Num;
}


function getscalespace(maxValue, scale) {
	return Math.ceil(maxValue / scale);
} 
	
	


</script>