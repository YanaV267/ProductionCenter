package com.dev.productioncenter.entity;

/**
 * @project Production Center
 * @author YanaV
 * The enum Enrollment status.
 */
public enum EnrollmentStatus {
    /**
     * Reserved enrollment status.
     */
    RESERVED,
    /**
     * Expired enrollment status.
     */
    EXPIRED,
    /**
     * Renewed enrollment status.
     */
    RENEWED,
    /**
     * Paid enrollment status.
     */
    PAID,
    /**
     * Approved enrollment status.
     */
    APPROVED;

    /**
     * Get status string.
     *
     * @return the string
     */
    public String getStatus(){
        return this.toString().toLowerCase();
    }
}
