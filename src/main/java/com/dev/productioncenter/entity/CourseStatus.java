package com.dev.productioncenter.entity;

public enum CourseStatus {
    UPCOMING,
    RUNNING,
    PAUSED;

    public String getStatus(){
        return this.toString().toLowerCase();
    }
}
