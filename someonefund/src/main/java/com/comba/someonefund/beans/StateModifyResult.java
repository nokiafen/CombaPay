package com.comba.someonefund.beans;

import java.io.Serializable;

/**
 * Created by chenhailin on 2017/7/13.
 */

public class StateModifyResult implements Serializable {

    /**
     * code : 0
     * bizCode : null
     * message : 修改成功
     * value : null
     */
    private int code;
    private String bizCode;
    private String message;
    private String value;

    public void setCode(int code) {
        this.code = code;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getBizCode() {
        return bizCode;
    }

    public String getMessage() {
        return message;
    }

    public String getValue() {
        return value;
    }
}
