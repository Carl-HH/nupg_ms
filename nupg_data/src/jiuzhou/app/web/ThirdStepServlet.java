/**
 * Program  : FourthStep.java
 * Author   : dh.shen
 * Create   : 2016-1-4 上午11:49:21
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
import jiuzhou.util.AESUtil;
import jiuzhou.util.CommonUtil;
import jiuzhou.util.MD5Util;

/**
 * 交互第三步.
 * @author dh.shen
 * @since
 * @param
 */
public class ThirdStepServlet extends HttpServlet{

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
		
		//1.验证session是否有效，机顶盒是否已经验证过
		if(!nupgService.sessionValidate(sessionid,3)){
			//验证失败!
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.print("ERR_AUTH");
			return;
		}
		//2.校验code
		String modelName = nupgService.getModelName();
		//code
		boolean ostype = CommonUtil.getOSType();
		String Kpath = "";
		String Apath = "";
		if(ostype){
			Kpath = request.getSession().getServletContext().getRealPath("\\MODELDATA\\"+modelName+"\\data-K.bin");
			Apath = request.getSession().getServletContext().getRealPath("\\MODELDATA\\"+modelName+"\\data-A.bin");
		}else{
			Kpath = request.getSession().getServletContext().getRealPath("/MODELDATA/"+modelName+"/data-K.bin");
			Apath = request.getSession().getServletContext().getRealPath("/MODELDATA/"+modelName+"/data-A.bin");
		}
		byte[] kBytes = CommonUtil.readFileContents(Kpath);
		byte[] ABytes = CommonUtil.readFileContents(Apath);
		byte[] KABytes = CommonUtil.byteMerger(kBytes, ABytes);
		byte[] MD5Bytes = MD5Util.MD5Encrypt(KABytes);
		
		System.out.println("MD5:"+CommonUtil.byte2hexStr(MD5Bytes));
		String dynamiccode = nupgService.getDynamiccode();
		System.out.println("dynamiccode:"+dynamiccode);
		String ivpath = "";
		if(ostype){
			ivpath = request.getSession().getServletContext().getRealPath("\\MODELDATA\\"+modelName+"\\aes128.IV");
		}else{
			ivpath = request.getSession().getServletContext().getRealPath("/MODELDATA/"+modelName+"/aes128.IV");
		}
		byte[] aesiv = CommonUtil.readFileContents(ivpath);
		String dynamicAesCode = CommonUtil.byte2hexStr(AESUtil.AESCBCEncrypt(MD5Bytes,aesiv,dynamiccode.getBytes()));
		System.out.println("dynamicAesCode:"+dynamicAesCode);
		if(!dynamicAesCode.equals(code)){
			System.out.println("code check failure!");
			nupgService.backStatus(sessionid);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.print("ERR_AUTH");
			return;
		}else{
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.print("OK_CODE");
			return;
		}
		//2.返回升级文件
		//设置文件MIME类型  
        /*response.setContentType(getServletContext().getMimeType(modelName+".sdl"));  
        response.setHeader("Content-Disposition", "attachment;filename="+modelName+".sdl");
		//获取配置文件路径
		String path = "";
		if(ostype){
			path = request.getSession().getServletContext().getRealPath("\\MODELDATA\\"+modelName+"\\"+modelName+".sdl");
		}else{
			path = request.getSession().getServletContext().getRealPath("/MODELDATA/"+modelName+"/"+modelName+".sdl");
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
        outs.close();*/
	}
}
