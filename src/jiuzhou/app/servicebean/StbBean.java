/**
 * Program  : StbBean.java
 * Author   : dh.shen
 * Create   : 2015-12-28 下午8:02:52
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
 * stb.
 * @author dh.shen
 * @since
 * @param
 */
public class StbBean {

	private int id;
	
	private String mac;
	
	private String sn;
	
	private String chipid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getChipid() {
		return chipid;
	}

	public void setChipid(String chipid) {
		this.chipid = chipid;
	}
	
	
}
