package com.meizu.code.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MeituanRecordBean {
    @Id(autoincrement = true)
    private Long _id;
    private int year;
    private int month;
    private int day;
    private int singleQuantity;

    @Generated(hash = 507953094)
    public MeituanRecordBean(Long _id, int year, int month, int day,
                             int singleQuantity) {
        this._id = _id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.singleQuantity = singleQuantity;
    }

    @Generated(hash = 850962746)
    public MeituanRecordBean() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSingleQuantity() {
        return this.singleQuantity;
    }

    public void setSingleQuantity(int singleQuantity) {
        this.singleQuantity = singleQuantity;
    }
}
