package com.crud.test;

import com.crud.bean.Employee;
import com.crud.dao.DepartmentMapper;
import com.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * com.crud.test
 *
 * @author jh
 * @date 2018/7/27 21:33
 * description:测试Mybatis的方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;

    /**
     * 测试DepartmentMapper
     */
    @Test
    public void testCRUD() {
        /*//创建SpringIOC容器
        ApplicationContext ioc=new ClassPathXmlApplicationContext ("applicationContext.xml");
        //从容器获取mapper*/
        System.out.println (departmentMapper);
//插入部门
        /*departmentMapper.insertSelective (new Department (null,"开发部"));
        departmentMapper.insertSelective (new Department (null,"测试部"));*/
        //员工插入
        //employeeMapper.insertSelective (new Employee (null,"magic","M","magic@qq.com",1));
        //批量插入------>使用可以执行批量操作的sqlsession
       /* for () {
            employeeMapper.insertSelective (new Employee (null, "magic", "M", "magic@qq.com", 1));
        }*/

        EmployeeMapper mapper = sqlSession.getMapper (EmployeeMapper.class);
        for (int i = 0; i <1000 ; i++) {
            String uid = UUID.randomUUID ().toString ().substring (0, 5)+i;
            mapper.insertSelective (new Employee (null,uid,"M",uid+"@qq.com",1));
        }
        System.out.println ("aaaaa");
    }

}
