package com.kzsrm.model;

/**
 * 课程表
 */
public class Course {
	private int id;
	private String type;
	private String cname;
	private String cprofile;
	private String caddress;
	private String pid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCprofile() {
		return cprofile;
	}
	public void setCprofile(String cprofile) {
		this.cprofile = cprofile;
	}
	public String getCaddress() {
		return caddress;
	}
	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}

}
