/**
 * Program  : Identify.java
 * Author   : dh.shen
 * Create   : 2015-10-23 上午10:20:44
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
 * STB认证.
 * @author dh.shen
 * @since
 * @param
 */
public class Identify extends HttpServlet{

	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	
	private UpgService upgService;

	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        String mac = request.getParameter("mac");
        String version = request.getParameter("version");
        String sn = request.getParameter("sn");
        if(mac==null||version==null||sn==null){
        	out.print("ERR_AUTH");
        	return;
        }
        upgService = new UpgService();
        String resStr = upgService.mainTask(mac, sn, version);
        out.print(resStr);
	}

	public UpgService getUpgService() {
		return upgService;
	}
	public void setUpgService(UpgService upgService) {
		this.upgService = upgService;
	}
	
}
