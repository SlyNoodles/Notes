package com.hz.bean;

public class Department {
    private Integer pid;

    private String depname;

    public Department(){}

    public Department(Integer pid,String depname){
        this.pid=pid;
        this.depname=depname;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname == null ? null : depname.trim();
    }
}