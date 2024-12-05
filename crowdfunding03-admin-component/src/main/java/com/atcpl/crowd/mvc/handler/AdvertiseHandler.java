package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.Advertise;
import com.atcpl.crowd.mvc.config.SecurityAdmin;
import com.atcpl.crowd.service.api.AdvertiseService;
import com.atcpl.crowd.util.CrowdUtil;
import com.atcpl.crowd.util.OssProperties;
import com.atcpl.crowd.util.ResultEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.mvc.handler
 * @ClassName：AdvertiseHandler
 * @Date：2023/4/9 19:21
 * @Version：1.0.0
 * @Description TODO(广告控制器)
 */
@Controller
public class AdvertiseHandler {


    @Autowired
    AdvertiseService advertiseService;


    /**
     * 查询所有的广告
     * @param pageNum
     * @return
     */
    @ResponseBody
    @RequestMapping("/advertise/getAll/advertise.json")
    public PageInfo<Advertise> getAll(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){
        // 使用分页插件
        PageHelper.startPage(pageNum,10);
        // 查询数据，与PageHelper紧挨着的集合会分页
        List<Advertise> advertiseList = advertiseService.getAll();
        // 封装分页信息  参数5是分页条显示5页
        PageInfo<Advertise> pageInfo = new PageInfo<>(advertiseList,5);
        return pageInfo;
    }

    /**
     * 上传广告
     * @param file
     * @param advDesc
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/advertise/upload/picture.json")
    public ResultEntity<String> uploadPic(@RequestParam(value = "file") MultipartFile file, String advDesc) throws IOException {

        // OSS对象存储参数
        OssProperties ossProperties = new OssProperties();
        ossProperties.setAccessKeyId("自己的AccessKeyId");
        ossProperties.setAccessKeySecret("自己的AccessKeySecret");
        ossProperties.setBucketDoMain("自己的BucketDoMain);
        ossProperties.setBucketName("自己的BucketName");
        ossProperties.setEndPoint("自己的EndPoint");

        // 将文件上传到OSS服务器上
        ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOSS(ossProperties.getEndPoint(),
                ossProperties.getBucketName(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                ossProperties.getBucketDoMain(),
                file.getInputStream(),
                file.getOriginalFilename());

        // 给属性赋值
        Advertise advertise = new Advertise();
        advertise.setName(advDesc);
        advertise.setUrl(resultEntity.getData());
        advertise.setStatus("未审核");
        // 从SpringSecurity中获取当前登录的用户
        SecurityAdmin principal = (SecurityAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = principal.getOriginalAdmin().getId();
        advertise.setUserid(id);

        // 执行上传
        advertiseService.addAdvertise(advertise);

        // 返回上传的内容
        return ResultEntity.successWithoutData();
    }


}
