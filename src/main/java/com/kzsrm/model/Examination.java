package com.kzsrm.model;

public class Examination {
    private Integer id;

    private Integer cid;

    private String name;

    private Integer target;

    private Integer degree;

    private Integer coverpoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Integer getCoverpoint() {
        return coverpoint;
    }

    public void setCoverpoint(Integer coverpoint) {
        this.coverpoint = coverpoint;
    }
}