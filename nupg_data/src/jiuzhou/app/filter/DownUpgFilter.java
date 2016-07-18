/**
 * Program  : LoginFilter.java
 * Author   : dh.shen
 * Create   : 2015-11-10 下午3:17:13
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiuzhou.app.service.NupgService;

/**
 * 是否登录检测.
 * @author dh.shen
 * @since
 * @param
 */
public class DownUpgFilter implements Filter {

	/**
	 * (方法作用描述).
	 * @author dh.shen
	 * @create 2015-11-10 下午3:17:41
	 * @since
	 * @param
	 * @return
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * (方法作用描述).
	 * @author dh.shen
	 * @create 2015-11-10 下午3:17:41
	 * @since
	 * @param
	 * @return
	 */
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		
		String servletpath = request.getServletPath();
		//System.out.println("PATH:"+servletpath);
		if(servletpath.indexOf(".sdl")!=-1){
			//下载升级文件时判断
			String res = validateUpgReq(request, response);
			if(!res.equals("ok")){
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.print("ERR_SESSION");
				return;
			}
		}else{
			if(servletpath.indexOf("MODELDATA")!=-1){
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.print("ERR_SESSION");
				return;
			}
		}
		arg2.doFilter(request, response);
	}

	/**
	 * (方法作用描述).
	 * @author dh.shen
	 * @create 2015-11-10 下午3:17:41
	 * @since
	 * @param
	 * @return
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public String validateUpgReq(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String sessionid = request.getParameter("sessionid");
		NupgService nupgService = new NupgService();
		
		//1.验证session是否有效，机顶盒是否已经验证过
		if(!nupgService.sessionValidate(sessionid,4)){
			//验证失败!
			return "ERR";
		}
		return "ok";
		//2.校验code
		/*String modelName = nupgService.getModelName();
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
		if(MD5Bytes.length!=16){
			nupgService.backStatus(sessionid);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.print("ERR_FAILURE");
			return "ERR_FAILURE";
		}
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
			out.print("ERR_CODE");
			return "ERR_CODE";
		}
		return "ok";*/
	}
	

}
