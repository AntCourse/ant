package com.kzsrm.model;

public class Point {
    private Integer id;

    private String name;

    private String content;

    private String type;

    private Integer courseId;
    
    private String isLearn;
    
    private String videoId;
    
    private String videoAddr;

    private Double accuracy;// 关联字段，后期赋值
    private Integer subNum;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

	public String getIsLearn() {
		return isLearn;
	}

	public void setIsLearn(String isLearn) {
		this.isLearn = isLearn;
	}
	
	public Double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(Double accuracy) {
		this.accuracy = accuracy;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoAddr() {
		return videoAddr;
	}

	public void setVideoAddr(String videoAddr) {
		this.videoAddr = videoAddr;
	}

	public Integer getSubNum() {
		return subNum;
	}

	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}
}