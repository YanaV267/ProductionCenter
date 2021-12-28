package com.dev.productioncenter.entity;

public enum UserRole {
    ADMIN,
    TEACHER,
    USER,
    GUEST;

    public String getRole(){
        return this.toString().toLowerCase();
    }
}
