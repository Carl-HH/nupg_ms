/**
 * Program  : DownloadFilter.java
 * Author   : dh.shen
 * Create   : 2015-10-29 下午5:59:00
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用来过滤非法下载.
 * @author dh.shen
 * @since
 * @param
 */
public class DownloadFilter implements Filter{

	/**
	 * (方法作用描述).
	 * @author dh.shen
	 * @create 2015-10-29 下午5:59:56
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
	 * @create 2015-10-29 下午5:59:56
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
		/*String servletpath = request.getServletPath();
		if(!servletpath.equals("/identify")){
			String mac = (String) request.getSession().getAttribute("loginuser");
			if(mac==null){
				return;
			}else{
				System.out.println("Filter:"+mac);
			}
		}*/
		arg2.doFilter(request, response);
	}

	/**
	 * (方法作用描述).
	 * @author dh.shen
	 * @create 2015-10-29 下午5:59:57
	 * @since
	 * @param
	 * @return
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	
}
