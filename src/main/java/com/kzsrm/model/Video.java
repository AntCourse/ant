package com.kzsrm.model;

public class Video {
    private Integer id;

    private String name;

    private String content;

    private String address;
    
    private Integer playCount;
    
    private Integer timeSpan;
    
    private String weight;
    
    private String orgname;
    
    private String tname;

    private Integer isBanner;
    
    private String pic;
    
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Integer getPlayCount() {
		return playCount == null ? 0 : playCount;
	}

	public void setPlayCount(Integer playCount) {
		this.playCount = playCount;
	}

	public Integer getTimeSpan() {
		return timeSpan == null ? 0 : timeSpan;
	}

	public void setTimeSpan(Integer timeSpan) {
		this.timeSpan = timeSpan;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}
	
	public Integer getIsBanner() {
		return isBanner;
	}

	public void setIsBanner(Integer isBanner) {
		this.isBanner = isBanner;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

}