package com.harry2815.mvvmdemo.bean;

import java.io.Serializable;

/**
 * Created by zhanghai on 2019/5/30.
 * function：
 */
public class SubjectSimpleDTO implements Serializable {
    /**
     * id : dc8a604b-f017-4665-badf-74c2a154f090
     * name : 物理
     */
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
