package com.dev.productioncenter.entity;

public enum EnrollmentStatus {
    RESERVED,
    EXPIRED,
    RENEWED,
    PAID,
    APPROVED;

    public String getStatus(){
        return this.toString().toLowerCase();
    }
}
