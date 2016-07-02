/**
 * Program  : ModelBean.java
 * Author   : dh.shen
 * Create   : 2015-10-28 下午12:00:07
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
 * Model.
 * @author dh.shen
 * @since
 * @param
 */
public class ModelBean {
	
	private int id;
	
	private String corpname;
	
	private String model;
	
	private String hosturl;
	
	private String version;
	
	private int isforce;
	
	private int sum;
	
	

	/**
	 * @return the corpname
	 */
	public String getCorpname() {
		return corpname;
	}

	/**
	 * @param corpname the corpname to set
	 */
	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getHosturl() {
		return hosturl;
	}

	public void setHosturl(String hosturl) {
		this.hosturl = hosturl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	/**
	 * @return the isforce
	 */
	public int getIsforce() {
		return isforce;
	}

	/**
	 * @param isforce the isforce to set
	 */
	public void setIsforce(int isforce) {
		this.isforce = isforce;
	}
	
	
	
}
