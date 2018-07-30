package com.crud.controller;

import com.crud.bean.Employee;
import com.crud.bean.Msg;
import com.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
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

    /**
     * 单个删除方法二合一
     * 批量删除：1-2-3
     * 单个删除：1
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public Msg deleteEmpById(@PathVariable("ids") String ids) {
        //区分批量删除和单个删除
        if (ids.contains ("-")) {
            String[] split = ids.split ("-");
            //组装ids集合
            List<Integer> list = new ArrayList<> ();
            for (String s : split) {
                list.add(Integer.parseInt (s));
            }
            employeeService.deleteBatch (list);

        } else {
            int i = Integer.parseInt (ids);
            employeeService.deleteEmp (i);

        }
        return Msg.success ();
    }

    /**
     * 保存员工
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    public Msg saveEmp(Employee employee) {
        System.out.println ("将要更新的员工数据" + employee);
        employeeService.updateEmp (employee);
        return Msg.success ();
    }

    /**
     * 根据id查询员工
     */
    @ResponseBody
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    public Msg getEmp(@PathVariable("id") Integer id) {

        Employee employee = employeeService.getEmp (id);
        return Msg.success ().add ("emp", employee);
    }

    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkuser(@RequestParam("empName") String empName) {
        //判断用户名是否合法
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches (regx)) {
            return Msg.fail ().add ("va_msg", "用户名必须是2-5位中文或是6-16位英文的组合");
        }
        //数据库用户名匹配校验
        boolean b = employeeService.checkUser (empName);
        if (b) {
            return Msg.success ();
        } else {
            return Msg.fail ().add ("va_msg", "用户名已被注册");
        }
    }

    @RequestMapping(value = "/emps", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result) {
        if (result.hasErrors ()) {
            //校验失败，在模态框中显示校验失败的信息。
            List<FieldError> errors = result.getFieldErrors ();
            HashMap<String, Object> map = new HashMap<> ();
            for (FieldError error : errors) {
                System.out.println ("错误的字段名" + error.getField ());
                System.out.println ("错误信息" + error.getDefaultMessage ());
                map.put (error.getField (), error.getDefaultMessage ());
            }
            return Msg.fail ().add ("errorFields", map);
        } else {
            employeeService.saveEmpp (employee);
            return Msg.success ();
        }
    }

    @RequestMapping(value = "/emps")
    @ResponseBody
    public Msg getEmpWitnJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        PageHelper.startPage (pn, 5);

        List<Employee> emps = employeeService.getAll ();

        PageInfo page = new PageInfo (emps, 5);
        return Msg.success ().add ("pageInfo", page);
    }

    //@RequestMapping("/emps")
  /*  public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        //不是分页查询
        //引入PageHelper插件，在查询之前调用下面的方法,传入页码和每页大小
        PageHelper.startPage (pn, 5);

        List<Employee> emps = employeeService.getAll ();

        PageInfo page = new PageInfo (emps, 5);

        model.addAttribute ("pageInfo", page);

        return "list";
    }*/

}
