/**
 * Program  : GetModelServlet.java
 * Author   : dh.shen
 * Create   : 2015-12-16 上午9:27:39
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

/**
 * 获取型号.
 * @author dh.shen
 * @since
 * @param
 */
public class GetModelServlet extends HttpServlet{
	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	private UpgService upgService;
	private String keyword;
	private int type;
	private int id;
	private String modelname;
	

	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		type = Integer.parseInt(request.getParameter("type"));
		upgService = new UpgService();
		String resStr = "";
		if(type==1){
			keyword = request.getParameter("keyword");
			if(keyword==null){
				System.out.println("keyword is null");
				return;
			}
			resStr = upgService.getModelByKeyWord(keyword);
			out.print(resStr);
		}else if(type==2){
			id = Integer.parseInt(request.getParameter("id"));
			resStr = upgService.getModelDataStatus(id);
			out.print(resStr);
		}else if(type==3){	//清空机顶盒数据
			modelname = request.getParameter("modelname");
			resStr = upgService.emptyStbData(modelname);
			out.print(resStr);
		}else if(type==4){ 	//下载机顶盒数据
			modelname = request.getParameter("modelname");
			String path = this.getServletContext().getRealPath("\\uploadfile\\report_file");
			resStr = upgService.makeReports(modelname, path);
			out.print(resStr);
		}else if(type==5){	//删除型号Data
			id = Integer.parseInt(request.getParameter("id"));
			String path = this.getServletContext().getRealPath("\\uploadfile\\model_data");
			resStr = upgService.deleteModelData(id, path);
			out.print(resStr);
		}
		
	}
}
