package com.comba.someonefund.beans;

import java.io.Serializable;

/**
 * Created by chenhailin on 2017/7/13.
 */

public class LoginResult  implements Serializable{

    /**
     * code : 0
     * bizCode : null
     * message : 登录成功
     * value : {"industryName":null,"factory":"麓谷企业广场","address":"湖南长沙岳麓区","workTypeName":null,"industry":1,"showStatus":0,"updateTime":"2017-07-12 03:37:33","realName":"张三","password":"e10adc3949ba59abbe56e057f20f883e","phone":"18674610989","createTime":"2017-07-12 03:37:33","addressShow":1,"name":"jack","workType":1,"id":1,"email":"78546@qq.com"}
     */
    private int code=-1;
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

    public class ValueEntity implements  Serializable{
        /**
         * industryName : null
         * factory : 麓谷企业广场
         * address : 湖南长沙岳麓区
         * workTypeName : null
         * industry : 1
         * showStatus : 0
         * updateTime : 2017-07-12 03:37:33
         * realName : 张三
         * password : e10adc3949ba59abbe56e057f20f883e
         * phone : 18674610989
         * createTime : 2017-07-12 03:37:33
         * addressShow : 1
         * name : jack
         * workType : 1
         * id : 1
         * email : 78546@qq.com
         */
        private String industryName;
        private String factory;
        private String address;
        private String workTypeName;
        private int industry;
        private int showStatus;
        private String updateTime;
        private String realName;
        private String password;
        private String phone;
        private String createTime;
        private int addressShow;
        private String name;
        private int workType;
        private int id;
        private String email;

        public void setIndustryName(String industryName) {
            this.industryName = industryName;
        }

        public void setFactory(String factory) {
            this.factory = factory;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setWorkTypeName(String workTypeName) {
            this.workTypeName = workTypeName;
        }

        public void setIndustry(int industry) {
            this.industry = industry;
        }

        public void setShowStatus(int showStatus) {
            this.showStatus = showStatus;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setAddressShow(int addressShow) {
            this.addressShow = addressShow;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setWorkType(int workType) {
            this.workType = workType;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIndustryName() {
            return industryName;
        }

        public String getFactory() {
            return factory;
        }

        public String getAddress() {
            return address;
        }

        public String getWorkTypeName() {
            return workTypeName;
        }

        public int getIndustry() {
            return industry;
        }

        public int getShowStatus() {
            return showStatus;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getRealName() {
            return realName;
        }

        public String getPassword() {
            return password;
        }

        public String getPhone() {
            return phone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public int getAddressShow() {
            return addressShow;
        }

        public String getName() {
            return name;
        }

        public int getWorkType() {
            return workType;
        }

        public int getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }
    }
}
