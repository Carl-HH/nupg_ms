/**
 * Program  : ThirdStep.java
 * Author   : dh.shen
 * Create   : 2016-1-4 上午10:45:31
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiuzhou.app.service.NupgService;
import jiuzhou.util.CommonUtil;

/**
 * 机顶盒与服务器交互第二步.
 * @author dh.shen
 * @since
 * @param
 */
public class SecondStepServlet extends HttpServlet {

	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	
	private NupgService nupgService;
	
	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String sessionid = request.getParameter("sessionid");
		
		if(sessionid==null){
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("ERR_AUTH");	
        	return;
		}
		nupgService = new NupgService();
		//1.验证session是否有效，机顶盒是否已经验证过
		System.out.println("sessionid:"+sessionid);
		if(!nupgService.sessionValidate(sessionid,2)){
			//验证失败!
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.print("ERR_AUTH");
			return;
		}
		
		//2.发送配置文件
		String modelName = nupgService.getModelName().toUpperCase();
		//设置文件MIME类型  
        response.setContentType(getServletContext().getMimeType(modelName+".cfg"));  
        response.setHeader("Content-Disposition", "attachment;filename="+modelName+".cfg");
		//获取配置文件路径
        boolean ostype = CommonUtil.getOSType();
        String path = "";
        if(ostype){
        	path = request.getSession().getServletContext().getRealPath("\\MODELDATA\\"+modelName+"\\"+modelName+".cfg");
        } else {
        	path = request.getSession().getServletContext().getRealPath("/MODELDATA/"+modelName+"/"+modelName+".cfg");
        }
		//读取文件  
        InputStream in = new FileInputStream(path);  
        OutputStream outs = response.getOutputStream();  
        //写文件  
        int b;  
        while((b=in.read())!= -1)  
        {  
        	outs.write(b);  
        }  
        in.close();  
        outs.close();
	}
}
