package com.pp.community.controller;

import com.pp.community.service.AlphaService;
import com.pp.community.utils.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * TODO
 *
 * @author ss_419
 * @version 1.0
 * @date 2023/8/26 18:07
 */
@Controller
@RequestMapping("/alpha")
public class HelloController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("select")
    @ResponseBody
    public String select(){
        return alphaService.select();
    }

    @RequestMapping("hello")
    @ResponseBody// 返回JSON格式的数据
    public String hello(){
        return "Hello Spring Boot";
    }
    /**
     * 在请求方法上使用http
     *
     * 不携带参数发请求：http://localhost:8080/community/alpha/http
     * 携带参数发请求：http://localhost:8080/community/alpha/http?code=123
     */
    @RequestMapping("http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        // 获取请求数据
        // 请求行
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        // 请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            String value = request.getHeader(headerName);
            System.out.println(headerName + " => " + value);
        }
        // 请求体
        System.out.println(request.getParameter("code"));

        // 返回相应数据
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write("<em>Hello <h1>Spring Boot</h1></em>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // GET请求
    // 构造请求路径：/students?current=1&limit=10
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required = false,defaultValue = "1") int current,
            @RequestParam(name = "limit",required = false,defaultValue = "10") int limit){

        System.out.println("current = " + current);
        System.out.println("limit = " + limit);
        return "some students";
    }

    // 构造请求路径：/student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudentById(@PathVariable("id") int id){
        System.out.println("id = " + id);
        return "Student ID:" + id;
    }

    // POST请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        return "{Name:" + name+ ","+" age:"+age+"}";
    }
    // ModelAndView 响应HTML数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        // 传递参数
        mav.addObject("name","P_P");
        mav.addObject("age",18);
        // 映射到templates目录下的demo目录
        mav.setViewName("/demo/view");
        return mav;
    }

    // Model 映射html文件
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","P_P_P123");
        model.addAttribute("age",3);
        return "/demo/view";
    }
    
    // 响应JSON数据（异步请求）
    // Java对象 -》 JSON字符串 -》JS对象
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String , Object> getEmp(){
        Map<String, Object> emp = new HashMap<>();
        emp.put("name","PP");
        emp.put("age",123);
        emp.put("salary",9999.99);
        return emp;
    }

    /**
     * 返回集合形式的json字符串
     * @return
     */
    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String , Object>> getEmps(){
        List<Map<String , Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name","PP");
        emp.put("age",123);
        emp.put("salary",9999.99);
        list.add(emp);

        Map<String, Object> emp1 = new HashMap<>();
        emp1.put("name","PP1111");
        emp1.put("age",11111);
        emp1.put("salary",1119999.99);
        list.add(emp1);

        return list;
    }

    /**
     * Cookie实例
     */
    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        // 创建Cookie
        String value = CommunityUtil.generateUUID();
        System.out.println("value = " + value);
        Cookie cookie = new Cookie("code", value);
        // 设置cookie生效的范围
        cookie.setPath("/community/alpha");
        // 设置cookie生存事件
        cookie.setMaxAge(600);
        // 发送cookie
        response.addCookie(cookie);
        return "set cookie";
    }

    /**
     * 获取指定的cookie
     * @param code
     * @return
     */
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookieValue(@CookieValue("code") String code){
        return "cookie => "+code;
    }

    @RequestMapping(path = "/session/set",method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("value","pp-session");

        return session.getAttribute("id").toString();
    }

    @RequestMapping(path = "/session/get",method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session){
        System.out.println(session.getAttribute("id").toString());
        System.out.println(session.getAttribute("value").toString());
        Integer id = (Integer) session.getAttribute("id");
        String value = (String) session.getAttribute("value");
        return "id => "+ id + ",value => "+ value;
    }


    /**
     * 测试ajax实例
     *
     */
    @RequestMapping(path = "/ajax",method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name,int age){
        System.out.println(name);
        System.out.println(age);
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("age",age);

        return CommunityUtil.getJSONString(0,"okk",map);

    }

}
