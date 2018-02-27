package no.steras.opensamlbook.idpPojo;

import java.math.BigDecimal;
import java.util.Date;

public class DsUser {
    private Long id;

    private String loginName;

    private Date effDate;

    private Date expDate;

    private String lastName;

    private String firstName;

    private Long region;

    private Long sex;

    private Long accountStatus;

    private String phone;

    private String email;

    private Date lstLogonDate;

    private Date logonTrialDate;

    private Long lstLogonResult;

    private Integer logonFailCnt;

    private Date pwdChangeDate;

    private String password;

    private String pwdQuestion;

    private String pwdAnswer;

    private Long orgId;

    private String status;

    private Date updateTime;

    private Long operatorId;

    private Long accountType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    public Long getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Long accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLstLogonDate() {
        return lstLogonDate;
    }

    public void setLstLogonDate(Date lstLogonDate) {
        this.lstLogonDate = lstLogonDate;
    }

    public Date getLogonTrialDate() {
        return logonTrialDate;
    }

    public void setLogonTrialDate(Date logonTrialDate) {
        this.logonTrialDate = logonTrialDate;
    }

    public Long getLstLogonResult() {
        return lstLogonResult;
    }

    public void setLstLogonResult(Long lstLogonResult) {
        this.lstLogonResult = lstLogonResult;
    }

    public Integer getLogonFailCnt() {
        return logonFailCnt;
    }

    public void setLogonFailCnt(Integer logonFailCnt) {
        this.logonFailCnt = logonFailCnt;
    }

    public Date getPwdChangeDate() {
        return pwdChangeDate;
    }

    public void setPwdChangeDate(Date pwdChangeDate) {
        this.pwdChangeDate = pwdChangeDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPwdQuestion() {
        return pwdQuestion;
    }

    public void setPwdQuestion(String pwdQuestion) {
        this.pwdQuestion = pwdQuestion;
    }

    public String getPwdAnswer() {
        return pwdAnswer;
    }

    public void setPwdAnswer(String pwdAnswer) {
        this.pwdAnswer = pwdAnswer;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }
}