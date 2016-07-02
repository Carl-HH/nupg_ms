/**
 * Program  : LogBean.java
 * Author   : dh.shen
 * Create   : 2015-12-21 上午9:19:45
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
 * 日志表.
 * @author dh.shen
 * @since
 * @param
 */
public class LogBean {

	private int id;
	
	private String datetime;
	
	private String logcontent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getLogcontent() {
		return logcontent;
	}

	public void setLogcontent(String logcontent) {
		this.logcontent = logcontent;
	}
	
}
