package com.development.productioncenter.entity;

public enum UserRole {
    ADMIN,
    TEACHER,
    USER;

    public String getRole(){
        return this.toString().toLowerCase();
    }
}
