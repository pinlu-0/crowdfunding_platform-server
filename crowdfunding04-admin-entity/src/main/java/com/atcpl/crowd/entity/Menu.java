package com.atcpl.crowd.entity;

import java.util.ArrayList;
import java.util.List;
/**
 * @Author：cpl
 * @Package：com.test
 * @Version：1.0.0
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class Menu {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 父节点id
     */
    private Integer pid;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 结点附带的URL地址，《点击菜单结点要跳转的地址》
     */
    private String url;

    /**
     * 结点图标的logo
     */
    private String icon;

    /**
     * 避免空指针异常 在这里直接实例化
     */
    private List<Menu> children = new ArrayList<>();

    /**
     * 控制节点是否为打开状态，true为打开状态
     */
    private boolean open = true;

    public Menu() {
    }

    public Menu(Integer id, Integer pid, String name, String url, String icon, List<Menu> children, boolean open) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.url = url;
        this.icon = icon;
        this.children = children;
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", children=" + children +
                ", open=" + open +
                '}';
    }
}