package com.smal.sso.idpPojo;


import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "ds_user", schema = "sso", catalog = "")
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
    private Long logonFailCnt;
    private Date pwdChangeDate;
    private String password;
    private String pwdQuestion;
    private String pwdAnswer;
    private Long orgId;
    private String status;
    private Date updateTime;
    private Long operatorId;
    private Long accountType;

    @Id
    @Column(name = "id", nullable = false, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "login_name", nullable = true, length = 50)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "eff_date", nullable = true)
    public Date getEffDate() {
        return effDate;
    }

    public void setEffDate(Date effDate) {
        this.effDate = effDate;
    }

    @Basic
    @Column(name = "exp_date", nullable = true)
    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 100)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "first_name", nullable = true, length = 100)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "region", nullable = true, precision = 0)
    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    @Basic
    @Column(name = "sex", nullable = true, precision = 0)
    public Long getSex() {
        return sex;
    }

    public void setSex(Long sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "account_status", nullable = true, precision = 0)
    public Long getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Long accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "lst_logon_date", nullable = true)
    public Date getLstLogonDate() {
        return lstLogonDate;
    }

    public void setLstLogonDate(Date lstLogonDate) {
        this.lstLogonDate = lstLogonDate;
    }

    @Basic
    @Column(name = "logon_trial_date", nullable = true)
    public Date getLogonTrialDate() {
        return logonTrialDate;
    }

    public void setLogonTrialDate(Date logonTrialDate) {
        this.logonTrialDate = logonTrialDate;
    }

    @Basic
    @Column(name = "lst_logon_result", nullable = true, precision = 0)
    public Long getLstLogonResult() {
        return lstLogonResult;
    }

    public void setLstLogonResult(Long lstLogonResult) {
        this.lstLogonResult = lstLogonResult;
    }

    @Basic
    @Column(name = "logon_fail_cnt", nullable = true)
    public Long getLogonFailCnt() {
        return logonFailCnt;
    }

    public void setLogonFailCnt(Long logonFailCnt) {
        this.logonFailCnt = logonFailCnt;
    }

    @Basic
    @Column(name = "pwd_change_date", nullable = true)
    public Date getPwdChangeDate() {
        return pwdChangeDate;
    }

    public void setPwdChangeDate(Date pwdChangeDate) {
        this.pwdChangeDate = pwdChangeDate;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "pwd_question", nullable = true, length = 100)
    public String getPwdQuestion() {
        return pwdQuestion;
    }

    public void setPwdQuestion(String pwdQuestion) {
        this.pwdQuestion = pwdQuestion;
    }

    @Basic
    @Column(name = "pwd_answer", nullable = true, length = 100)
    public String getPwdAnswer() {
        return pwdAnswer;
    }

    public void setPwdAnswer(String pwdAnswer) {
        this.pwdAnswer = pwdAnswer;
    }

    @Basic
    @Column(name = "org_id", nullable = true, precision = 0)
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 1)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "operator_id", nullable = true, precision = 0)
    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    @Basic
    @Column(name = "account_type", nullable = true, precision = 0)
    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(Long accountType) {
        this.accountType = accountType;
    }

}