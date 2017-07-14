package com.comba.someonefund.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenhailin on 2017/7/13.
 */

public class IndustryWorktype implements Serializable{

    /**
     * code : 0
     * bizCode : null
     * message : 查询成功
     * value : {"workType":[{"name":"服务工程师","id":3},{"name":"售后","id":2},{"name":"售前","id":1}],"industry":[{"name":"CT","id":3},{"name":"IT软件","id":2},{"name":"IT硬件","id":1}]}
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

    public class ValueEntity  implements  Serializable{
        /**
         * workType : [{"name":"服务工程师","id":3},{"name":"售后","id":2},{"name":"售前","id":1}]
         * industry : [{"name":"CT","id":3},{"name":"IT软件","id":2},{"name":"IT硬件","id":1}]
         */
        private List<WorkTypeEntity> workType;
        private List<IndustryEntity> industry;

        public void setWorkType(List<WorkTypeEntity> workType) {
            this.workType = workType;
        }

        public void setIndustry(List<IndustryEntity> industry) {
            this.industry = industry;
        }

        public List<WorkTypeEntity> getWorkType() {
            return workType;
        }

        public List<IndustryEntity> getIndustry() {
            return industry;
        }

        public class WorkTypeEntity  implements  Serializable{
            /**
             * name : 服务工程师
             * id : 3
             */
            private String name;
            private int id;

            public void setName(String name) {
                this.name = name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }
        }

        public class IndustryEntity  implements  Serializable{
            /**
             * name : CT
             * id : 3
             */
            private String name;
            private int id;

            public void setName(String name) {
                this.name = name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }
        }
    }
}
