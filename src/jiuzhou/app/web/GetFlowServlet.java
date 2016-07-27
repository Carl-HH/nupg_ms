package jiuzhou.app.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import jiuzhou.app.service.FlowsService;
import jiuzhou.app.service.UpgService;

public class GetFlowServlet extends HttpServlet{

	private FlowsService flowsService;
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private String model;
	private String version;
	private int currentPage;
	private int type;
	
	/**
	 * (方法作用描述).
	 * @author chen.hua
	 * @create 2016年7月19日 下午2:09:47
	 * @since
	 * @param
	 * @return
	 */
	@Override
	public void service(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		type = Integer.parseInt(request.getParameter("type"));
		
		flowsService = new FlowsService();
		String resStr = "";
		
		if(1 == type) {
			model = request.getParameter("model");
			resStr = flowsService.getFlowsByModel(model, currentPage);
			out.print(resStr);
		}
		
		//String pb = flowsService.getFlowsByModel("6828", 1);
		
	}
	
}
