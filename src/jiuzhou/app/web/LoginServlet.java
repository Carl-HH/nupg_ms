/**
 * Program  : LoginServlet.java
 * Author   : dh.shen
 * Create   : 2015-10-27 上午9:55:52
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiuzhou.app.service.UpgService;


/**
 * 网络升级管理登陆.
 * @author dh.shen
 * @since
 * @param
 */
public class LoginServlet extends HttpServlet{

	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private UpgService upgService;
	
	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		UpgService upgService = new UpgService();
        PrintWriter out = response.getWriter();
        if(upgService.validateUser(username, pwd)){
        	if(upgService.validateAdmin(username)) {
        		logger.info(username + "login as admin");
        		out.print("admin");
        	} else {
        		logger.info(username +"login as corp");
        		out.print("corp");
        	}
        		
        }else{
        	logger.info(username +"login failed");
        	out.print("err");
        }
	}
}