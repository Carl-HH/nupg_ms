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
	
	private int issnupt;
	
	private String snstart;
	
	private String snend;
	
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

	/**
	 * @return the issnupt
	 */
	public int getIssnupt() {
		return issnupt;
	}

	/**
	 * @param issnupt the issnupt to set
	 */
	public void setIssnupt(int issnupt) {
		this.issnupt = issnupt;
	}

	/**
	 * @return the snstart
	 */
	public String getSnstart() {
		return snstart;
	}

	/**
	 * @param snstart the snstart to set
	 */
	public void setSnstart(String snstart) {
		this.snstart = snstart;
	}

	/**
	 * @return the snend
	 */
	public String getSnend() {
		return snend;
	}

	/**
	 * @param snend the snend to set
	 */
	public void setSnend(String snend) {
		this.snend = snend;
	}
	
	
	
}
