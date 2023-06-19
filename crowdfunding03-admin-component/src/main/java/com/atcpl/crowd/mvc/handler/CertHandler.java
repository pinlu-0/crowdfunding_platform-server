package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.Cert;
import com.atcpl.crowd.service.api.CertService;
import com.atcpl.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.mvc.handler
 * @ClassName：CertHandler
 * @Date：2023/4/3 15:34
 * @Version：1.0.0
 * @Description TODO(资质维护控制器)
 */
@Controller
public class CertHandler {

    @Autowired
    CertService certService;

    /**
     * 查询所有的资质并进行分页
     *
     * @param pageSize   每页显示多少数据
     * @param pageNumber 页数
     * @param model
     * @return
     */
    @RequestMapping("/cert/getallcert.html")
    public String getAllCert(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber, Model model) {
        // 查询所有的资质
        PageInfo<Cert> certPageInfo = certService.getAllCert(pageSize, pageNumber);
        model.addAttribute("certPageInfo", certPageInfo);
        return "cert-page";
    }

    /**
     * 添加资质
     *
     * @param cert
     * @return
     */
    @ResponseBody
    @RequestMapping("/cert/savecert.json")
    public ResultEntity<String> addCert(Cert cert) {
        certService.addCert(cert);
        return ResultEntity.successWithoutData();
    }

    /**
     * 修改资质信息
     *
     * @param cert
     * @return
     */
    @ResponseBody
    @RequestMapping("/cert/editcert.json")
    public ResultEntity<String> editCert(Cert cert) {
        certService.editCert(cert);
        return ResultEntity.successWithoutData();
    }

    /**
     * 删除资质信息
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/cert/remove.json")
    public ResultEntity<String> removeCert(@RequestParam("id") String ids) {
        if (!ids.trim().equals("")) {
            certService.deleteCert(ids);
        }
        return ResultEntity.successWithoutData();
    }

}
