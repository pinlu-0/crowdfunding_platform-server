package com.atcpl.crowd.entity;

public class Auth {
    private Integer id;
    /**
     * 给资源分配权限时使用的具体值。权限验证也是使用name字段值进行比较
     */
    private String name;
    /**
     * 在页面上显示的值，便于用户查看。
     */
    private String title;
    /**
     * 权限所属的类。是个自连接
     */
    private Integer categoryId;

    public Auth() {

    }

    public Auth(Integer id, String name, String title, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.categoryId = categoryId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}