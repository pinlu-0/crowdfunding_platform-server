package com.atcpl.crowd.entity;

public class AccountTypeCert {
    private Integer id;

    private Integer accttypeid;

    private Integer certid;

    public AccountTypeCert() {
    }

    public AccountTypeCert(Integer accttypeid, Integer certid) {
        this.accttypeid = accttypeid;
        this.certid = certid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccttypeid() {
        return accttypeid;
    }

    public void setAccttypeid(Integer accttypeid) {
        this.accttypeid = accttypeid;
    }

    public Integer getCertid() {
        return certid;
    }

    public void setCertid(Integer certid) {
        this.certid = certid;
    }
}