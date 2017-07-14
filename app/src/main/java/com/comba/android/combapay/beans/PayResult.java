package com.comba.android.combapay.beans;

import java.io.Serializable;

/**
 * Created by chenhailin on 2017/6/21.
 */

public class PayResult implements Serializable {


    /**
     * code : 0
     * bizCode : null
     * message : 操作成功
     * value : {"orderId":1111,"tnCode":1111}
     */
    private int code;
    private String bizCode;
    private String message;
    private ValueEntity value;

    public void setCode(int code) {
        this.code = code;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValue(ValueEntity value) {
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

    public ValueEntity getValue() {
        return value;
    }

    public class ValueEntity {
        /**
         * orderId : 1111
         * tnCode : 1111
         */
        private int orderId;
        private String tnCode;

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public void setTnCode(String tnCode) {
            this.tnCode = tnCode;
        }

        public int getOrderId() {
            return orderId;
        }

        public String getTnCode() {
            return tnCode;
        }
    }
}
