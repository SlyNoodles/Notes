package com.hz.service.impl;

import com.hz.bean.Employee;
import com.hz.bean.EmployeeExample;
import com.hz.dao.EmployeeMapper;
import com.hz.service.EmployeeService;
import com.hz.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 新增
     * @param employee
     */
    @Override
    public void save(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void remove(Employee employee) {

    }

    /**
     * id删除
     * @param id
     */
    @Override
    public void removeById(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量id删除
     * @param id
     */
    @Override
    public void removeByIdList(List<Integer> id) {
        EmployeeExample exampleEx=new EmployeeExample();
        EmployeeExample.Criteria criteria=exampleEx.createCriteria();
        criteria.andEidIn(id);
        employeeMapper.deleteByExample(exampleEx);
    }

    /**
     * 更新
     * @param employee
     */
    @Override
    public void update(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

    /**
     * id查询员工数据
     * @param id
     * @return
     */
    @Override
    public Object getById(Integer id) {
        return employeeMapper.selectByPrimaryKeyWithDept(id);
    }

    /**
     * 查询所有员工数据
     * @return
     */
    @Override
    public List<Employee> listAll() {
        List<Employee> list=employeeMapper.selectByExampleWithDept(null);
        for(Employee employee:list){
            System.out.println(employee+"--------------\n");

        }
        return list;
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public Long countCriteria(Employee employee) {
        return null;
    }

    /**
     * 校验用户名是否存在
     * @param empname
     * @return
     */
    @Override
    public boolean checkUser(String empname) {
        EmployeeExample employeeEx=new EmployeeExample();
        EmployeeExample.Criteria criteria=employeeEx.createCriteria();
        criteria.andEmpnameEqualTo(empname);
        Long count=employeeMapper.countByExample(employeeEx);
        return count==0;
    }
}
