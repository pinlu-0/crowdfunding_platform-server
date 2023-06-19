package com.atcpl.crowd.mvc.handler;

import com.atcpl.crowd.entity.Menu;
import com.atcpl.crowd.service.api.MenuService;
import com.atcpl.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cpl
 * @date 2022/11/3
 * @apiNote
 */
@RestController
public class MenuHandler {
    @Autowired
    MenuService menuService;

    /**
     * 删除树节点
     *
     * @param nodeId
     * @return
     */
    @RequestMapping("/menu/remove.json")
    public ResultEntity<String> removeTreeNode(@RequestParam("id") Integer nodeId) {
        menuService.removeTreeNode(nodeId);
        return ResultEntity.successWithoutData();
    }


    /**
     * 修改树节点
     *
     * @param menu
     * @return
     */
    @RequestMapping("/menu/edit.json")
    public ResultEntity<String> editTreeNode(Menu menu) {
        menuService.updateTreeNode(menu);
        return ResultEntity.successWithoutData();
    }

    /**
     * 添加数节点
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/menu/save.json", method = RequestMethod.POST)
    public ResultEntity<String> saveTreeNode(Menu menu) {
        menuService.saveTreeNode(menu);
        return ResultEntity.successWithoutData();
    }


    /**
     * 查询菜单树
     *
     * @return
     */
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTreeNew(HttpSession session) {
        // 查询全部的Menu对象
        List<Menu> list = menuService.getAll();

        // 存储根结点的变量
        Menu root = null;

        // 用来存储id和menu对象的对应关系便于查找父结点
        Map<Integer, Menu> map = new HashMap<>();

        // 遍历集合  将每个menu对象与其对应的id组成一个map对象
        for (Menu menu : list) {
            Integer id = menu.getId();
            map.put(id, menu);
        }
        for (Menu menu : list) {
            // 获取当前menu对象的pid
            Integer pid = menu.getPid();

            // 如果pid为null, 则该menu为根结点
            if (pid == null) {
                root = menu;
                // 停止本次循环，继续执行下次循环
                continue;
            }
            // 如果pid不为null，说明当前节点有父节点，可以根据pid到map中找对应的menu对象
            Menu fatherNode = map.get(pid);
            // 将当前节点存入该父节点的集合中
            fatherNode.getChildren().add(menu);

        }

        // 返回根结点（也就是返回整个树）
        return ResultEntity.successWithData(root);
    }

}
