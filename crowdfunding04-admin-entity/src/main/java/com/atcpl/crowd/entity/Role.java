package com.atcpl.crowd.entity;

/**
 * @author 蜡笔小新
 * @apiNote 角色实体类
 */
public class Role {
    private Integer id;

    private String name;

    private Integer menuId;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Role(Integer id, String name, Integer menuId) {
        this.id = id;
        this.name = name;
        this.menuId = menuId;
    }

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}