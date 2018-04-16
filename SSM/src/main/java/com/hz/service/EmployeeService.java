package com.hz.service;

import com.hz.bean.Employee;

public interface EmployeeService extends BaseService<Employee>{
    //查询用户名
    boolean checkUser(String empname);
}
