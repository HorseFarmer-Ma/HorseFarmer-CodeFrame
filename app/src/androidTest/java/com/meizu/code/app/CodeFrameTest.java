/*
 * ************************************************************
 * Class：CodeFrameTest.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-02 10:15:09
 * Last modified time：2018-11-02 10:15:09
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app;


import android.support.test.runner.AndroidJUnit4;

import com.meizu.code.app.bean.DuplicateBean;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RunWith(AndroidJUnit4.class)
public class CodeFrameTest {
    @Test
    public void testDuplicate() {
        List<DuplicateBean> listData = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            DuplicateBean bean = new DuplicateBean(String.valueOf(i % 10), "maxueming", "25");
            listData.add(bean);
        }
        List<DuplicateBean> duplicateBeans = removeDuplicate(listData);
        int size = duplicateBeans.size();
        System.out.print("数量 = " + size);
    }

    private List<DuplicateBean> removeDuplicate(List<DuplicateBean> list) {
        Set<DuplicateBean> duplicateBeanSet = new TreeSet<DuplicateBean>(new Comparator<DuplicateBean>() {
            @Override
            public int compare(DuplicateBean o1, DuplicateBean o2) {
                int i = o1.getDuplicate().compareTo(o2.getDuplicate());
                if (i == 0) {
                    o2.setDuplicateCount(o2.getDuplicateCount() + 1);
                }
                return i;
            }
        });
        duplicateBeanSet.addAll(list);
        return new ArrayList<>(duplicateBeanSet);
    }
}
