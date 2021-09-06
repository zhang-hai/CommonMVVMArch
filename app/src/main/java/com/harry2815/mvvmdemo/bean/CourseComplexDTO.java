package com.harry2815.mvvmdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghai on 2019/5/30.
 * function：
 */
public class CourseComplexDTO implements Serializable {
    private TermComplexDTO termComplexDTO;
    private SubjectSimpleDTO subjectSimpleDTO;
    private List<ClassComplexDTO> classComplexDTOS;

    public TermComplexDTO getTermComplexDTO() {
        return termComplexDTO;
    }

    public void setTermComplexDTO(TermComplexDTO termComplexDTO) {
        this.termComplexDTO = termComplexDTO;
    }

    public SubjectSimpleDTO getSubjectSimpleDTO() {
        return subjectSimpleDTO;
    }

    public void setSubjectSimpleDTO(SubjectSimpleDTO subjectSimpleDTO) {
        this.subjectSimpleDTO = subjectSimpleDTO;
    }

    public List<ClassComplexDTO> getClassComplexDTOS() {
        return classComplexDTOS;
    }

    public void setClassComplexDTOS(List<ClassComplexDTO> classComplexDTOS) {
        this.classComplexDTOS = classComplexDTOS;
    }
}