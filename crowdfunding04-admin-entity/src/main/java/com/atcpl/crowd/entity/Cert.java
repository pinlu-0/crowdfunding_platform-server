package com.atcpl.crowd.entity;

/**
 * 资质实体类
 * @author 蜡笔小新
 */
public class Cert {
    private Integer id;

    private String name;

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
}