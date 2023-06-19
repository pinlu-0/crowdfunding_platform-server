package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.Admin;
import com.atcpl.crowd.entity.Student;
import com.atcpl.crowd.service.api.AdminService;
import com.atcpl.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    AdminService adminService;


    /**
     * 测试Ajax异步工作原理
     * @return
     * @throws InterruptedException
     */
    @ResponseBody
    @RequestMapping("/test/ajax/async.html")
    public String testAjaxAsync() throws InterruptedException {
        Thread.sleep(2000);
        return "success";
    }



    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student){
        // 将收到的数据封装到ResultEntity中返回
        ResultEntity<Student> studentResultEntity = ResultEntity.successWithData(student);
        return studentResultEntity;
    }


    @ResponseBody
    @RequestMapping("/send/array.html")
    public String testReceiveArray(@RequestBody List<Integer> arrays){
        for(Integer array :  arrays){
            System.out.println("======="+array);
        }
        return "success";
    }

    /**
     * 因为web.xml文件中servlet-pattern 设置的拦截方式是带扩展名的，所以这里的请求带.html
     * @param model
     * @return
     */
    @RequestMapping("/test/ssm.html")
    public String testSSM(Model model){
        List<Admin> admins = adminService.getAllAdmin();
        model.addAttribute("admins",admins);
        return "success";
    }




}
