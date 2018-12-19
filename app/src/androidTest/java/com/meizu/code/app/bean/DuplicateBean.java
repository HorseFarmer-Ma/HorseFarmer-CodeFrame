/*
 * ************************************************************
 * Class：DuplicateBean.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-02 10:20:13
 * Last modified time：2018-11-02 10:20:13
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.bean;

public class DuplicateBean {
    private String duplicate;
    private String name;
    private String age;
    private int duplicateCount;   // 重复次数

    public DuplicateBean(String duplicate, String name, String age) {
        this.duplicate = duplicate;
        this.name = name;
        this.age = age;
    }

    public String getDuplicate() {
        return duplicate;
    }

    public void setDuplicate(String duplicate) {
        this.duplicate = duplicate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getDuplicateCount() {
        return duplicateCount;
    }

    public void setDuplicateCount(int duplicateCount) {
        this.duplicateCount = duplicateCount;
    }
}
