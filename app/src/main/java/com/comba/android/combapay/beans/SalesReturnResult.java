package com.comba.android.combapay.beans;

import java.io.Serializable;

/**
 * Created by chenhailin on 2017/6/21.
 */

public class SalesReturnResult implements Serializable {
    /**
     * code : 0
     * bizCode : null
     * message : 操作成功
     * value : 98818499564232704
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
