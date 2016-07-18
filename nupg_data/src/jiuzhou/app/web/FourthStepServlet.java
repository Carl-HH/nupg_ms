/**
 * Program  : FifthStepServlet.java
 * Author   : dh.shen
 * Create   : 2016-1-4 下午2:21:05
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

import jiuzhou.app.service.NupgService;

/**
 * 机顶盒返回升级状态信息.
 * @author dh.shen
 * @since
 * @param
 */
public class FourthStepServlet extends HttpServlet {

	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	
	private NupgService nupgService;
	
	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String sessionid = request.getParameter("sessionid");
		nupgService = new NupgService();
		//1.验证session是否有效，机顶盒是否已经验证过
		if(!nupgService.sessionValidate(sessionid,5)){
			//验证失败!
			out.print("ERR_AUTH");
			return;
		}
		nupgService.updateSTBVersion(sessionid);
		out.print("BYE");
		return;
	}
}
