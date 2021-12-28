package com.dev.productioncenter.entity;

public enum UserStatus {
    ACTIVE,
    BLOCKED;

    public String getStatus(){
        return this.toString().toLowerCase();
    }
}
