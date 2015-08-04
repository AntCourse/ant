package com.kzsrm.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author wwy
 *
 */
public class Sign {
	private int id;
	private int uid;
	private Date lastSignDay;
	private Date startSignDay;

	public Date getStartSignDay() {
		return startSignDay;
	}

	public void setStartSignDay(Date startSignDay) {
		this.startSignDay = startSignDay;
	}

	private int antCoin;
	public int getAntCoin() {
		return antCoin;
	}

	public void setAntCoin(int antCoin) {
		this.antCoin = antCoin;
	}

	private String signNum;

	public String getSignNum() {
		return signNum;
	}

	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getLastSignDay() {
		return lastSignDay;
	}

	public void setLastSignDay(Date lastSignDay) {
		this.lastSignDay = lastSignDay;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[id=" + id + ", uid=" + uid + ", lastSignDay=" + lastSignDay + ",startSignDay=" + startSignDay
				+",antCoin = " + antCoin + ", signNum=" + signNum + "]";
	}
}
