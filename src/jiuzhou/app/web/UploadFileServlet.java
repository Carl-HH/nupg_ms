/**
 * Program  : UploadFileServlet.java
 * Author   : dh.shen
 * Create   : 2015-12-22 上午10:37:08
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jiuzhou.app.service.FileParseService;
import jiuzhou.util.LogUtil;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

/**
 * 用于接收报盘文件和模型Data的servlet.
 * @author dh.shen
 * @since
 * @param
 */
public class UploadFileServlet extends HttpServlet{

	/** 变量描述. */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private FileParseService fileParseService;
	private int filetype;
	private int modelid;
	
	public void service(HttpServletRequest request,HttpServletResponse response) 
            throws ServletException,IOException{
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String newFileName = null; 
		String resStr = "ok";
		try {  
            DiskFileItemFactory fileFactory = new DiskFileItemFactory();  
            ServletFileUpload fu = new ServletFileUpload(fileFactory);  
            @SuppressWarnings("rawtypes")
			List fileItems = fu.parseRequest(request);  
            @SuppressWarnings("rawtypes")
			Iterator iter = fileItems.iterator();  
            List<String> fileNames = new ArrayList<String>();  
            
            while (iter.hasNext()) {  
                FileItem item = (FileItem) iter.next();  
                if (!item.isFormField()) { //是文件  
                    String oldFileName = item.getName();  
                    int delimiter = oldFileName.lastIndexOf("/");  
                    if (delimiter == -1){
                    	newFileName = oldFileName.substring(delimiter + 1);  
                    } else {
                    	newFileName = oldFileName;
                    } 
                    fileNames.add(newFileName);  
                    String savePath = getSavePath()+"\\"+ newFileName;
                    item.write(new File(savePath));  
                }else { //是表单  
                    String itemName = item.getFieldName();  
                    //匹配表单项的name属性值来判断是哪一个项。
                    if ("filetype".equals(itemName)) {  
                    	filetype = Integer.parseInt(item.getString());  
                    }
                    if("modelid".equals(itemName)){
                    	modelid = Integer.parseInt(item.getString());
                    }
                }  
            }
            //根据选择的文件类型将文件转移到指定目录下
            File tmpfile = new File(getSavePath()+"\\"+ newFileName);
			if(!tmpfile.exists()){
				out.print("操作失败!");
				
				return;
			}
			fileParseService = new FileParseService();
			String modelname = LogUtil.getModeNameById(modelid);
			int index = newFileName.lastIndexOf(".");
        	String postfix = newFileName.substring(index);
        	
        	Calendar c = Calendar.getInstance(); 
    		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    		String dateStr = sdf.format(c.getTime());
        	
            if(filetype==1){	//报盘文件
            	String savepath = getSavePath()+"\\report_file\\"+ modelname+"_"+dateStr+postfix;
	    		File dest = new File(savepath);
	    		if(dest.exists()){
	    			out.print("请等待上一个文件解析完毕!");
					return;
	    		}
	    		tmpfile.renameTo(dest);
	    		resStr = fileParseService.parseReport(modelname,savepath);
	    		if(!resStr.equals("ok")){
	    			out.print(resStr);
					return;
	    		}
	    		LogUtil.addLog("上传型号："+modelname+"报盘文件并解析成功!");
            }else if(filetype==2){	//型号数据文件
	    		File dest = new File(getSavePath()+"\\model_data\\"+ modelname+postfix);
	    		tmpfile.renameTo(dest);
	    		if(!fileParseService.changeDataState(modelid,1)){
	    			out.print("后台修改Data状态失败!");
					return;
	    		}
	    		LogUtil.addLog("上传型号："+modelname+"数据DATA成功!");
            }
        } catch (Exception e) {  
        	e.printStackTrace();
        	resStr = "异常0";
        } 
		out.print(resStr);
	}
	
	//返回上传文件保存的物理位置
	public String getSavePath() {
		return this.getServletContext().getRealPath("\\uploadfile");
		
	}
	
}
