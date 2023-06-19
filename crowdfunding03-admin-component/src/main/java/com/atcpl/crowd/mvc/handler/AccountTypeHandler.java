package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.AccountType;
import com.atcpl.crowd.entity.AccountTypeCert;
import com.atcpl.crowd.entity.Cert;
import com.atcpl.crowd.service.api.AccountTypeCertService;
import com.atcpl.crowd.service.api.AccountTypeService;
import com.atcpl.crowd.service.api.CertService;
import com.atcpl.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author：cpl
 * @Package：com.atcpl.crowd.mvc.handler
 * @ClassName：AccountTypeHandler
 * @Date：2023/4/5 16:37
 * @Version：1.0.0
 * @Description TODO(账户分类控制器)
 */
@Controller
public class AccountTypeHandler {

    @Autowired
    CertService certService;

    @Autowired
    AccountTypeService accountTypeService;

    @Autowired
    AccountTypeCertService accountTypeCertService;

    /**
     * 查询所有的账户资质
     *
     * @return
     */
    @RequestMapping("/type/getalltype.html")
    public String getAllAccountType(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {

        // 先查出表格横向显示的数据：账户类型
        List<AccountType> accountTypeList = accountTypeService.getAllAccountType();

        model.addAttribute("accountTypeList", accountTypeList);
        // 再查表格出纵向显示的数据：资质类型
        PageInfo<Cert> certPageInfo = certService.getAllCert(pageSize, pageNum);
        model.addAttribute("certPageInfo", certPageInfo);
        // 查出横纵交叉的坐标，使用二维矩阵

        // 查询中间表，查出账户和资质的对应关系
        List<AccountTypeCert> accountTypeCerts = certService.getAccountTypeCerts();

        // 声明一个二维数组
        Boolean[][] relations = new Boolean[certPageInfo.getList().size()][accountTypeList.size()];

        // 遍历横向长度
        for (int i = 0; i < relations.length; i++) {
            // 遍历纵向长度
            for (int j = 0; j < relations[i].length; j++) {
                // 拿出当前的资质
                Cert cert = certPageInfo.getList().get(i);
                // 拿出当前类型
                AccountType accountType = accountTypeList.get(j);
                // 对照中间表是否有
                for (AccountTypeCert innerAcctTypeCert : accountTypeCerts) {
                        relations[i][j] = innerAcctTypeCert.getAccttypeid().equals(accountType.getId()) && innerAcctTypeCert.getCertid().equals(cert.getId());
                    if (relations[i][j] == true) {
                        break;
                    }
                }
            }
            model.addAttribute("relations", relations);
        }
        return "type";
    }

    /**
     * 选中或者取消
     * @param certId
     * @param acctId
     * @return
     */
    @ResponseBody
    @RequestMapping("/type/checked/cert.json")
    public ResultEntity<String> checkedAcctCert(@RequestParam("certId") Integer certId , @RequestParam("acctId") Integer acctId){

        // 判断是否已经选中了
        List<AccountTypeCert> list = accountTypeCertService.relationExist(certId, acctId);
        if(list.size()==0){
            // 往中间表添加选中的关系
            accountTypeCertService.checkAcctCertRelation(certId,acctId);
            return ResultEntity.successWithoutData();
        }else{
            // 删除选中的这个关系
            accountTypeCertService.removeAcctCertRelation(certId,acctId);
            return ResultEntity.successWithData("");
        }


    }



}
