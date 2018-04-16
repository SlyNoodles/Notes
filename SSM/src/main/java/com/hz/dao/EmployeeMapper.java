package com.hz.dao;

import com.hz.bean.Employee;
import com.hz.bean.EmployeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(Integer eid);

    int insert(Employee record);

    int insertSelective(Employee record);

    /**
     * 单表查询（条件可有可无）
     * @param example
     * @return
     */
    List<Employee> selectByExample(EmployeeExample example);

    /**
     * id查询
     * @param eid
     * @return
     */
    Employee selectByPrimaryKey(Integer eid);

    /**
     * 关联查询（条件可有可无）
     * @param example
     * @return
     */
    List<Employee> selectByExampleWithDept(EmployeeExample example);

    /**
     * id关联查询
     * @param eid
     * @return
     */
    Employee selectByPrimaryKeyWithDept(Integer eid);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}