package com.kzsrm.model;

public class Subject {
    private Integer id;

    private String question;

    private String type;

    private Integer degree;

    private String hint;

    private String allcount;

    private String rightcount;

    private String avgtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint == null ? null : hint.trim();
    }

    public String getAllcount() {
        return allcount;
    }

    public void setAllcount(String allcount) {
        this.allcount = allcount == null ? null : allcount.trim();
    }

    public String getRightcount() {
        return rightcount;
    }

    public void setRightcount(String rightcount) {
        this.rightcount = rightcount == null ? null : rightcount.trim();
    }

    public String getAvgtime() {
        return avgtime;
    }

    public void setAvgtime(String avgtime) {
        this.avgtime = avgtime == null ? null : avgtime.trim();
    }
}