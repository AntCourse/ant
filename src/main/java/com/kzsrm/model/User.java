package com.kzsrm.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class User {
	private int id;
	private String name;
	private String sex;
	private int age;
	private String phone;
	private String passwd;
	private String email;
	@DateTimeFormat(iso = ISO.DATE_TIME,pattern="yyyy-MM-dd")
	private Date birthday;
	private String qq;
	private Date regtime;
	private Date logintime;
	private String avator;
	private String sign;
	private String level;
	private int regnum;
	private Date lastreg;
	private String honor;
	private String learntime;
	private String status;
	private String examtype;
	private int coin;
	private String yzm;
	
	
	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}

	public Date getLastreg() {
		return lastreg;
	}

	public void setLastreg(Date lastreg) {
		this.lastreg = lastreg;
	}

	public String getHonor() {
		return honor;
	}

	public void setHonor(String honor) {
		this.honor = honor;
	}

	public String getLearntime() {
		return learntime;
	}

	public void setLearntime(String learntime) {
		this.learntime = learntime;
	}

	public String getExamtype() {
		return examtype;
	}

	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}

	public int getRegnum() {
		return regnum;
	}

	public void setRegnum(int regnum) {
		this.regnum = regnum;
	}

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", sex=" + sex + ", age=" + age + ", phone=" + phone + ", passwd="
				+ passwd + ", email=" + email + ", birthday=" + birthday + ", qq=" + qq + ", sign=" + sign
				+ ", regtime=" + regtime + ", logintime=" + logintime + ", status=" + status + ", avator=" + avator
				+ ", sign=" + sign + ", level=" + level + ", regnum=" + regnum + ", coin=" + coin + ", yzm=" + yzm + ", lastreg="
				+ lastreg + ", honor=" + honor + ", learntime=" + learntime + ", status=" + status + ", examtype="
				+ examtype + "]";
	}
}
