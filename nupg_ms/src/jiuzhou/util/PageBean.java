/**
 * Program  : PageBean.java
 * Author   : dh.shen
 * Create   : 2015-8-18 下午3:13:51
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.util;

import java.util.List;

/**
 * (类作用描述).
 * @author dh.shen
 * @since
 * @param
 */
public class PageBean {

	private int totalPage ;    //总页数
	
	private int currentPage;     //当前页码
	
	private int pageSize;        //每页放的记录的条数
	
	private int totalSize;       //记录的总条数
	
	private List pageList;        //每页要显示内容的集合
	
	public PageBean(){}
	
	/**
	 * 
	 * 
	 * @author niej
	 * @create 2009-1-15 下午06:09:47
	 * @since 初始化PageBean的静态工厂
	 * @param currentPage  当前页码
	 * @param pageSize     每页放的记录的条数
	 * @param totalSize    记录的总条数
	 * @param pageList     每页要显示内容的集合
	 * @return
	 * 
	 */
	public static PageBean getInstance(int currentPage,int pageSize,int totalSize,List pageList){
		
		 //计算总页数 begin
		 int totalpage = totalSize/pageSize;
		 
         if(totalSize % pageSize != 0){
        	 totalpage = totalpage + 1;
         }
         
         if(totalpage == 0)
         {
        	 totalpage = 1;	
         }
         //计算总页数 end 
         
         
         //验证页面的记录 begin
         if(pageList == null || pageList.size() == 0)
         {
        	 return null;
         }
         
         if(pageList.size() > pageSize)
         {
        	 return null;
         }
         //验证页面的记录 end
         
         
         //设置PAGEBEAN的属性 begin
         Object[] object = new Object[pageList.size()];
         for(int i = 0 ; i < pageList.size() ; i ++)
         {
        	 object[i] = pageList.get(i);
         }
		 
         PageBean pageBean = new PageBean();
         pageBean.setCurrentPage(currentPage);
         pageBean.setPageSize(pageSize);
         pageBean.setTotalPage(totalpage);
         pageBean.setTotalSize(totalSize);
         pageBean.setPageList(pageList);
        // pageBean.setObject(object);
         //设置PAGEBEAN的属性 end
         
		return pageBean;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List getPageList() {
		return pageList;
	}

	public void setPageList(List pageList) {
		this.pageList = pageList;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

}

