package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.util.ResultEntity;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.mvc.handler
 * @ClassName：ProcessHandler
 * @Date：2023/4/7 14:40
 * @Version：1.0.0
 * @Description TODO(流程管理控制器)
 */
@Controller
public class ProcessHandler {

    /**
     * 流程引擎服务
     */
    @Autowired
    RepositoryService repositoryService;


    /**
     * 来到流程管理页面
     *
     * @return
     */
    @RequestMapping("/process/to/process/page.html")
    public String toProcessPage() {
        // 查询
        return "process-page";
    }

    /**
     * 预览流程定义的图片
     *
     * @param processDefinitionId
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/process/get/process/img.json")
    public byte[] getProcessImg(@RequestParam("pid") String processDefinitionId) throws IOException {
        // 获取流程定义
        ProcessDefinition definition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        // 获取流程id
        String deploymentId = definition.getDeploymentId();
        List<String> list = repositoryService.getDeploymentResourceNames(deploymentId);
        // 获取到图片的名
        String pngName = "";
        for (String string : list) {
            if (string.endsWith(".png")) {
                pngName = string;
            }
        }
        // 流程定义 与  部署信息 一一对应  按照找部署查找资源
        // 按照部署id和图片名查出图片
        InputStream stream = repositoryService.getResourceAsStream(deploymentId, pngName);
        byte[] bytes = IOUtils.toByteArray(stream);
        // 关闭流 节省资源
        stream.close();
        return bytes;
    }


    /**
     * 查询部署总数量
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/process/getJSON")
    public long getJSON() {
        long count = repositoryService.createProcessDefinitionQuery().count();
        return count;
    }


    /**
     * 查询所有的流程信息
     *
     * @param pageNum
     * @return
     */
    @ResponseBody
    @RequestMapping("/process/getAll/process.json")
    public List<Map<String, Object>> getAllProcess(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Map<String, Object>> pd = null;
        try {
            // 查询所有流程数据  该服务不能使用PageHelper插件进行分页，其内部已经定义好分页方法了
            pd = new ArrayList<>();
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().listPage((pageNum - 1) * 5, 5);
            for (ProcessDefinition process : list) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", process.getId());
                map.put("name", process.getName());
                map.put("key", process.getKey());
                map.put("version", process.getVersion());
                map.put("deploymentId", process.getDeploymentId());
                pd.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
    }

    /**
     * 上传流程定义文件
     *
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/process/upload/process.json")
    public ResultEntity<String> uploadProcess(@RequestParam("file") MultipartFile file) {
        // 部署流程
        try {
            Deployment deploy = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), file.getInputStream())
                    .deploy();
            return ResultEntity.successWithoutData();
        } catch (IOException e) {
            e.printStackTrace();
            return ResultEntity.failed("未知错误信息；错误代码：0");
        }
    }


    /**
     *删除流程定义文件
     * @param processDefinitionId
     * @return
     */
    @ResponseBody
    @RequestMapping("/process/delete/process/file.json")
    public ResultEntity<String> deleteProcess(@RequestParam("processDefinitionId") String processDefinitionId) {

        // 级联删除流程定义信息，即使该流程有流程实例启动也可以删除，设置为false非级别删除方式，如果流程
        repositoryService.deleteDeployment(processDefinitionId, true);
        return ResultEntity.successWithoutData();
    }

}
