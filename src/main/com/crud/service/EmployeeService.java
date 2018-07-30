package com.crud.service;

import com.crud.bean.Employee;
import com.crud.bean.EmployeeExample;
import com.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.crud.service
 *
 * @author jh
 * @date 2018/7/28 12:39
 * description:
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept (null);
    }

    public void saveEmpp(Employee employee) {
        employeeMapper.insertSelective (employee);
    }

    public boolean checkUser(String empName) {
        EmployeeExample example = new EmployeeExample ();
        EmployeeExample.Criteria criteria = example.createCriteria ();
        criteria.andEmpNameEqualTo (empName);
        long l = employeeMapper.countByExample (example);
        return l == 0;
    }

    //按照员工信息，查询员工
    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey (id);
        return employee;
    }

    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective (employee);
    }

    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey (id);
    }

    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample ();
        EmployeeExample.Criteria criteria = example.createCriteria ();
        criteria.andEmpIdIn (ids);
        employeeMapper.deleteByExample (example);
    }
}
