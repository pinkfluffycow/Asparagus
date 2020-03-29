package com.example.asparagus;

public class Achievement {
    private String name;
    private String description;
    private Integer maxValue;
    private Integer currValue;

    public Achievement() {}

    public Achievement(String name, String desc, Integer maxVal, Integer currVal) {
        this.name = name;
        this.description = desc;
        this.maxValue = maxVal;
        this.currValue = Math.min(currVal, maxVal);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getCurrValue() {
        return currValue;
    }

    public void setCurrValue(Integer currValue) {
        this.currValue = currValue;
    }

}
