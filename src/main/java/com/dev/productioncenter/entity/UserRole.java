package com.dev.productioncenter.entity;

/**
 * @project Production Center
 * @author YanaV
 * The enum User role.
 */
public enum UserRole {
    /**
     * Admin user role.
     */
    ADMIN,
    /**
     * Teacher user role.
     */
    TEACHER,
    /**
     * User user role.
     */
    USER,
    /**
     * Guest user role.
     */
    GUEST;

    /**
     * Get role string.
     *
     * @return the string
     */
    public String getRole(){
        return this.toString().toLowerCase();
    }
}
