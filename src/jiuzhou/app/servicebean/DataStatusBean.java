/**
 * Program  : DataStatusBean.java
 * Author   : dh.shen
 * Create   : 2015-12-28 下午5:26:48
 *
 *
 * Copyright 2014 by Shenzhen JIUZHOU Electronic CO.,LTD,
 * All rights reserved.
 *
 *
 * (文档描述).
 */
package jiuzhou.app.servicebean;

/**
 * 数据状态json.
 * @author dh.shen
 * @since
 * @param
 */
public class DataStatusBean {

	private int id;
	
	private int count;	//此型号机顶盒的数量
		
	private int	datastatus;	//是否存在型号数据,0:不存在,1:存在
	
	private String datapath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getDatastatus() {
		return datastatus;
	}

	public void setDatastatus(int datastatus) {
		this.datastatus = datastatus;
	}

	public String getDatapath() {
		return datapath;
	}

	public void setDatapath(String datapath) {
		this.datapath = datapath;
	}
}
