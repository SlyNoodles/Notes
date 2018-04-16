package com.hz.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hz.bean.Employee;
import com.hz.bean.Msg;
import com.hz.service.EmployeeService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工
 */
@Controller
public class EmployeeController {

    @Resource(name="employeeServiceImpl")
    EmployeeService employeeService;

    /**
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单个删除：1
     * @param ids
     * @return
     */
    @RequestMapping(value = "/empid/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg getDelete(@PathVariable("ids") String ids){
        //判断ids字符串里面有没有包含-字符（有就代表着批量删除，没有就是id删除）
        if(ids.contains("-")){
            List<Integer> del_ids=new ArrayList<>();
            String[] str_ids=ids.split("-");
            for(String string:str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.removeByIdList(del_ids);
        }else {
            Integer id = Integer.parseInt(ids);
            employeeService.removeById(id);
        }
        return Msg.success();
    }


    /**
     * 如果直接发送ajax=PUT形式的请求
     * 封装的数据
     * Employee
     * [empId=1014, empName=null, gender=null, email=null, dId=null]
     *
     * 问题：
     * 请求体中有数据；
     * 但是Employee对象封装不上；
     * update tbl_emp  where emp_id = 1014;
     *
     * 原因：
     * Tomcat：
     * 		1、将请求体中的数据，封装一个map。
     * 		2、request.getParameter("empName")就会从这个map中取值。
     * 		3、SpringMVC封装POJO对象的时候。
     * 				会把POJO中每个属性的值，request.getParamter("email");
     * AJAX发送PUT请求引发的血案：
     * 		PUT请求，请求体中的数据，request.getParameter("empName")拿不到
     * 		Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
     * org.apache.catalina.connector.Request--parseParameters() (3111);
     *
     * protected String parseBodyMethods = "POST";
     * if( !getConnector().isParseBodyMethod(getMethod()) ) {
     success = true;
     return;
     }
     *
     *
     * 解决方案；
     * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
     * 1、web配置上HttpPutFormContentFilter；
     *  <filter>
     *      <filter-name>HttpPutFormContentFilter</filter-name>
     *      <filter-class>org.springframework.web.filter.HttpPutFormContentFilter</filter-class>
     *  </filter>
     *  <filter-mapping>
     *      <filter-name>HttpPutFormContentFilter</filter-name>
     *      <url-pattern>/*</url-pattern>
     *  </filter-mapping>
     * 2、他的作用；将请求体中的数据解析包装成一个map。
     * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
     * 员工更新方法
     * @param employee
     * @return
     */
    @RequestMapping(value = "/empid/{eid}",method = RequestMethod.PUT)
    @ResponseBody
    public Msg saveEmp(Employee employee, HttpServletRequest request){

        System.out.println("请求中的值："+request.getParameter("eid")+"\n");
        System.out.println("将要更新的员工数据："+employee);

        employeeService.update(employee);
        return Msg.success();
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @RequestMapping(value = "/empid/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmpid(@PathVariable("id")Integer id){

        Employee employee= (Employee) employeeService.getById(id);
        return Msg.success().add("emp",employee);
    }

    /**
     * 新增员工信息
     * @return
     */
    @RequestMapping(value="/emp",method= RequestMethod.POST)
    @ResponseBody
    public Msg saveemp(@Valid Employee employee, BindingResult result){
        if(result.hasErrors()){
            Map<String,Object> map=new HashMap<>();
            List<FieldError> errors=result.getFieldErrors();
            for(FieldError fieldError:errors){
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误的信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }else{
            employeeService.save(employee);
            return Msg.success();
        }
    }

    /**
     * 校验员工姓名
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg getCheckuser(@RequestParam("empName") String empName){
        //jquery的使用正则表达式
        //先判断用户名是否是合法的表达式;
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        //判断员工姓名是否符合正则表达式规范
        if(!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名可以是2-5位中文或者6-16位英文和数字的组合");
        }

        //校验姓名查询
        boolean b=employeeService.checkUser(empName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg","已存在该用户名");
        }
    }

    /**
     * 导入jackson包。
     * ajax请求
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
        // 这不是一个分页查询
        // 引入PageHelper分页插件
        // 在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn, 5);
        // startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.listAll();
        // 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了。
        // 封装了详细的分页信息,包括有我们查询出来的数据，传入连续显示的页数
        PageInfo page = new PageInfo(emps, 5);
        return Msg.success().add("pageInfo", page);
    }

    /**
     * 普通的查询方式
     * 查询所有员工数据（分页查询    ）
     * @return
     */
    @RequestMapping("/lemp")
    public String getEmps(@RequestParam(value="pn",defaultValue = "1")Integer pn, Model model){
        System.out.println("进来了getEmps方法\n");
        //这不是一个分页查询
        //引入一个pageHelper分页插件
        //再查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn,5);
        //List<Employee> listemp=new ArrayList<Employee>();
        List<Employee> listemp=employeeService.listAll();
        //使用PageInfo包装查询后的结果，只要把PageInfo交给页面就可以了
        //封装了分页的详细信息，包括我们查询的数据
        PageInfo page=new PageInfo(listemp,5);
        model.addAttribute("pageInfo",page);
        System.out.println("getEmps方法--pn:"+pn);
        return "listemp";
    }
}
