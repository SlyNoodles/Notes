package com.hz.bean;

import javax.validation.constraints.Pattern;

public class Employee {
    private Integer eid;

    //使用注解方式在后台判断格式,内外双重校验，防止注入提交
    @Pattern(regexp = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})"
            ,message = "用户名必须是2-5位中文或者6-16位英文和数字的组合")
    private String empname;

    @Pattern(regexp = "^1[89]|[2-5]\\d|60$"
            ,message = "请填写18岁到60岁之间的年龄")
    private Integer age;

    private Integer depid;
    //想查询该表信息的时候把关联表的信息一起查询出来
    private Department department;

    public Employee(){}

    public Employee(Integer eid, String empname, Integer age, Integer depid) {
        this.eid=eid;
        this.empname=empname;
        this.age=age;
        this.depid=depid;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee [eid=" + eid + ", empname=" + empname
                + ", age=" + age + ", depid=" + depid
                + "]";
    }
}