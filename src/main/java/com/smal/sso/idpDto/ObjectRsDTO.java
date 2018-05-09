package com.smal.sso.idpDto;

import java.io.Serializable;

public class ObjectRsDTO implements Serializable {
    private int code;
    private String message;
    private Object content; //消息主体内容

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
