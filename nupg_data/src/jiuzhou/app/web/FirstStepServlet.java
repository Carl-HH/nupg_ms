/**
 * Program  : SecondStepServlet.java
 * Author   : dh.shen
 * Create   : 2015-12-31 下午2:53:18
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
 * 机顶盒交互第一步.
 * 验证session是否还有效、解密客户端上传数据验证客户端、返回key
 * @author dh.shen
 * @since
 * @param
 */
public class FirstStepServlet extends HttpServlet {

	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	
	private NupgService nupgService;
	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String sessionid = request.getParameter("sessionid");
		String code = request.getParameter("code");
		if(sessionid==null||code==null){
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("ERR_AUTH");	
        	return;
		}
		nupgService = new NupgService();
		
		boolean osType = CommonUtil.getOSType();	//true:windows;false:linux;
		
		String datapath = "";
		if(osType){
			datapath = request.getSession().getServletContext().getRealPath("\\MODELDATA\\");
		}else{
			datapath = request.getSession().getServletContext().getRealPath("/MODELDATA/");
		}
		
		if(!nupgService.FirstStepFunc(sessionid,code,datapath)){
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print("ERR_AUTH");//identification
			return;
		}
		//返回K'
		//设置文件MIME类型  
        response.setContentType(getServletContext().getMimeType("KK.bin"));  
        response.setHeader("Content-Disposition", "attachment;filename="+"KK.bin");
		//获取K'文件路径
        String path = "";
        if(osType){
        	path = request.getSession().getServletContext().getRealPath("\\MODELDATA\\"+nupgService.getModelName().toUpperCase()+"\\data-K-sign.bin");
		} else {
			path = request.getSession().getServletContext().getRealPath("/MODELDATA/"+nupgService.getModelName().toUpperCase()+"/data-K-sign.bin");
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
