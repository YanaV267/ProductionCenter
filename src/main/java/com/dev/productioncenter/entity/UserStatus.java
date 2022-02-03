package com.dev.productioncenter.entity;

/**
 * @project Production Center
 * @author YanaV
 * The enum User status.
 */
public enum UserStatus {
    /**
     * Active user status.
     */
    ACTIVE,
    /**
     * Blocked user status.
     */
    BLOCKED;

    /**
     * Get status string.
     *
     * @return the string
     */
    public String getStatus(){
        return this.toString().toLowerCase();
    }
}
