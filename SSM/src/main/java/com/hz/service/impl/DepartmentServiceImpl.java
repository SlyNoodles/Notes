package com.hz.service.impl;

import com.hz.bean.Department;
import com.hz.dao.DepartmentMapper;
import com.hz.service.DepartmentService;
import com.hz.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void save(Department department) {

    }

    @Override
    public void remove(Department department) {

    }

    @Override
    public void removeById(Integer id) {

    }

    @Override
    public void removeByIdList(List<Integer> id) {

    }

    @Override
    public void update(Department department) {

    }

    @Override
    public Object getById(Integer id) {
        return null;
    }

    @Override
    public List<Department> listAll() {
        return departmentMapper.selectByExample(null);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public Long countCriteria(Department department) {
        return null;
    }
}
