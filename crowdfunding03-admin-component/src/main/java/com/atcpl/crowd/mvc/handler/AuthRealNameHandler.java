package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.TMember;
import com.atcpl.crowd.entity.TMemberCert;
import com.atcpl.crowd.service.api.MemberCertService;
import com.atcpl.crowd.service.api.MemberService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.mvc.handler
 * @ClassName：AuthRealNameController
 * @Date：2023/4/18 18:16
 * @Version：1.0.0
 * @Description TODO(实名认证审核控制器)
 */
@Controller
public class AuthRealNameHandler {


    @Autowired
    TaskService taskService;

    @Autowired
    MemberService memberService;

    @Autowired
    MemberCertService memberCertService;

    @RequestMapping("/auth/real/name/list.html")
    public String list(Model model){

        // 查出待审核的任务
        List<Task> tasks = taskService.createTaskQuery().taskName("人工审核").list();

        List<Map<String,Object>> pageContent = new ArrayList<>();
        for(Task task: tasks ){
            Map<String,Object> map = new HashMap<>();
            map.put("taskId",task.getId());
            map.put("processId",task.getProcessInstanceId());
            map.put("taskName",task.getName());

            // 查member基本信息
            TMember member = memberService.selectMemberInfoByTicket(task.getProcessInstanceId());
            map.put("member",member);

            // 查资质图片
            List<TMemberCert> certs =  memberCertService.selectCertsByTicketId(task.getProcessInstanceId());
            map.put("certs", certs);
            pageContent.add(map);
        }
        model.addAttribute("pageInfo", pageContent);
        return "auth_cert";
    }

    @ResponseBody
    @RequestMapping("/complete/auth/real/name.json")
    public String completeAuth(Integer memberId,String taskId){
        taskService.complete(taskId);
        TMember tMember = new TMember();
        tMember.setId(memberId);
        tMember.setAuthstatus(2);
        Integer i = memberService.updateAuthStatus(tMember);
        return "SUCCESS";
    }


}
