package com.comba.someonefund.beans;

import java.io.Serializable;

/**
 * Created by chenhailin on 2017/7/12.
 */
public class SearchResult implements Serializable {
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocals() {
        return locals;
    }

    public void setLocals(String locals) {
        this.locals = locals;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    String phone;
    String name;
    String locals;
    String workType;
    String  profession;

}
