/**
 * Program  : ConfigServlet.java
 * Author   : dh.shen
 * Create   : 2015-10-28 上午10:33:52
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import jiuzhou.app.service.UpgService;
import jiuzhou.util.LogUtil;

/**
 * 配置逻辑处理servlet.
 * @author dh.shen
 * @since
 * @param
 */
public class ConfigServlet extends HttpServlet{

	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private UpgService upgService;
	
	private String corpname;
	private String modelname;
	private String hosturl;
	private String version;
	private int type;
	
	private int currentPage;
	private String keyword;
	private int id;
	private int isforce;
	private int issnupt;
	private String snstart;
	private String snend;
	
	
	public final void service(final HttpServletRequest request,final HttpServletResponse response) 
            throws ServletException,IOException{
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		type = Integer.valueOf(request.getParameter("type"));
		
		UpgService upgService = new UpgService();
		
		if(type==1){	//添加型号
			corpname = request.getParameter("corpname");
			modelname = request.getParameter("model");
			hosturl = request.getParameter("hosturl");
			version = request.getParameter("version");
			String resStr = upgService.addModel(corpname,modelname, hosturl, version);
			LogUtil.addLog("添加新的型号:[型号:"+modelname+"]  [数据服务器URL:"+hosturl+"]  [版本:"+version+"]");
			out.print(resStr);
		}
		
		if(type==2){	//获取Model，根据页码、关键字
			currentPage = Integer.valueOf(request.getParameter("currentPage"));
			if(currentPage<1){
				currentPage = 1;
			}
			keyword = request.getParameter("keyword");
			String resStr = upgService.getModelTab(currentPage, keyword);
			out.print(resStr);
		}
		
		if(type==3){	//修改型号
			corpname = request.getParameter("corpname");
			modelname = request.getParameter("model");
			hosturl = request.getParameter("hosturl");
			version = request.getParameter("version");
			id = Integer.valueOf(request.getParameter("id"));
			String resStr = upgService.modifyModelById(id,corpname,modelname, hosturl, version);
			LogUtil.addLog("修改id为"+id+"的 [型号:"+modelname+"],[hosturl："+hosturl+"],[version:"+version+"]");
			out.print(resStr);
		}
		
		if(type==4){	//删除model信息
			id = Integer.valueOf(request.getParameter("id"));
			String modelname = LogUtil.getModeNameById(id);
			String resStr = upgService.deleModelById(id);
			LogUtil.addLog("删除id为"+id+"的型号 "+modelname);
			out.print(resStr);
		}
		
		if(type==5){  //设置强制升级以及升级控制
			isforce = Integer.valueOf(request.getParameter("isforce"));		
			issnupt = Integer.valueOf(request.getParameter("issnupt"));
			snstart = request.getParameter("snstart");
			snend = request.getParameter("snend");
			id = Integer.valueOf(request.getParameter("id"));
			String modelname = LogUtil.getModeNameById(id);
			String resStr = upgService.modifyUptSettingById(id,isforce,issnupt,snstart,snend);
			LogUtil.addLog("修改id为"+id+"型号:"+modelname+"的机器强制升级模式为："+isforce);
			out.print(resStr);
		}
	}

}
