package com.dokidokiujn.sprinngboot.entity;

import lombok.Getter;

@Getter
public class Course {
    private Integer id;
    private String code;
    private String name;
    private Float credit;
    private String teacher;
    private String classTime;
    private String location;
    private String nature;
    private Integer selectedCount;
    private Integer totalCapacity;
    private Boolean isSelected;

    public Course() {
    }

    public Course(Integer id, String code, String name, Float credit, String teacher, String classTime, String location, String nature, Integer selectedCount, Integer totalCapacity, Boolean isSelected) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.teacher = teacher;
        this.classTime = classTime;
        this.location = location;
        this.nature = nature;
        this.selectedCount = selectedCount;
        this.totalCapacity = totalCapacity;
        this.isSelected = isSelected;
    }
}
