package com.crud.controller;

import com.crud.bean.Employee;
import com.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * com.crud.controller
 *
 * @author jh
 * @date 2018/7/28 12:37
 * description:处理员工CRUD请求
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        //不是分页查询
        //引入PageHelper插件，在查询之前调用下面的方法,传入页码和每页大小
        PageHelper.startPage (pn, 5);

        List<Employee> emps = employeeService.getAll ();

        PageInfo page = new PageInfo (emps,5);

        model.addAttribute ("pageInfo",page);

        return "list";
    }

}
