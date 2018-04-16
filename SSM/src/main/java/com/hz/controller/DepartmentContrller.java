package com.hz.controller;

import com.hz.bean.Department;
import com.hz.bean.Msg;
import com.hz.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 处理和部门有关的请求
 */
@Controller
public class DepartmentContrller {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/dep")
    @ResponseBody
    public Msg getdep() {
        //查询部门所有信息
        List<Department> list=departmentService.listAll();
        return Msg.success().add("depts",list);
    }
}
