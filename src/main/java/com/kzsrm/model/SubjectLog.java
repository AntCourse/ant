package com.kzsrm.model;

import java.util.Date;

public class SubjectLog {
    private Integer id;

    private String sid;

    private String userid;

    private Integer isright;

    private String answerid;

    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getIsright() {
		return isright;
	}

	public void setIsright(Integer isright) {
		this.isright = isright;
	}

	public String getAnswerid() {
		return answerid;
	}

	public void setAnswerid(String answerid) {
		this.answerid = answerid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}