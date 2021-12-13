package com.development.productioncenter.entity;

public enum EnrollmentStatus {
    RESERVED,
    PAID,
    APPROVED;

    public String getStatus(){
        return this.toString().toLowerCase();
    }
}
