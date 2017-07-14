package com.comba.android.combapay.beans;

import java.io.Serializable;

/**
 * Created by chenhailin on 2017/6/21.
 */

public class TradeNumResult implements Serializable {
    /**
     * code : 0
     * bizCode : null
     * message : 操作成功
     * value : {"tnCode":"870133461144330687400"}
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
         * tnCode : 870133461144330687400
         */
        private String tnCode;
        private String orderId;

        public void setTnCode(String tnCode) {
            this.tnCode = tnCode;
        }

        public String getTnCode() {
            return tnCode;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

//    /**
//     * code : 0
//     * bizCode : null
//     * message : 操作成功
//     * value : 98818499564232704
//     */
//    private int code;
//    private String bizCode;
//    private String message;
//    private String value;
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public void setBizCode(String bizCode) {
//        this.bizCode = bizCode;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public String getBizCode() {
//        return bizCode;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getValue() {
//        return value;
//    }


}
