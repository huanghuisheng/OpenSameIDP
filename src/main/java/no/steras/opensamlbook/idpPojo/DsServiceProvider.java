package no.steras.opensamlbook.idpPojo;

import java.math.BigDecimal;
import java.util.Date;

public class DsServiceProvider {
    private Long id;

    private String seviceName;

    private Date registerTime;

    private String serviceUrl;

    private String status;

    private Integer uploadStrategy;

    private Integer checkStrategy;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeviceName() {
        return seviceName;
    }

    public void setSeviceName(String seviceName) {
        this.seviceName = seviceName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUploadStrategy() {
        return uploadStrategy;
    }

    public void setUploadStrategy(Integer uploadStrategy) {
        this.uploadStrategy = uploadStrategy;
    }

    public Integer getCheckStrategy() {
        return checkStrategy;
    }

    public void setCheckStrategy(Integer checkStrategy) {
        this.checkStrategy = checkStrategy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}