/**
 * Program  : GetLogServlet.java
 * Author   : dh.shen
 * Create   : 2015-12-21 上午8:52:45
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

import jiuzhou.app.service.UpgService;

/**
 * (类作用描述).
 * @author dh.shen
 * @since
 * @param
 */
public class GetLogServlet extends HttpServlet{

	/** 变量描述. */
	private static final long serialVersionUID = 1L;

	private int currentPage;
	private String keyword;
	private UpgService upgService;
	
	
	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		
		upgService = new UpgService();
		keyword = request.getParameter("keyword");
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		if(currentPage<1){
			currentPage = 1;
		}
		keyword = request.getParameter("keyword");
		String resStr = upgService.getLogList(currentPage, keyword);
		out.print(resStr);
	}
	
	
}
