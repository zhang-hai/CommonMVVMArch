package com.harry2815.mvvmdemo.bean;

import java.io.Serializable;

/**
 * Created by zhanghai on 2019/5/30.
 * function：
 */
public class ClassComplexDTO implements Serializable {
    private ClassSimpleDTO classSimpleDTO;
    private GradeComplexDTO gradeComplexDTO;

    public ClassSimpleDTO getClassSimpleDTO() {
        return classSimpleDTO;
    }

    public void setClassSimpleDTO(ClassSimpleDTO classSimpleDTO) {
        this.classSimpleDTO = classSimpleDTO;
    }

    public GradeComplexDTO getGradeComplexDTO() {
        return gradeComplexDTO;
    }

    public void setGradeComplexDTO(GradeComplexDTO gradeComplexDTO) {
        this.gradeComplexDTO = gradeComplexDTO;
    }
}
