package com.dev.productioncenter.entity;

/**
 * @project Production Center
 * @author YanaV
 * The enum Course status.
 */
public enum CourseStatus {
    /**
     * Upcoming course status.
     */
    UPCOMING,
    /**
     * Running course status.
     */
    RUNNING,
    /**
     * Paused course status.
     */
    PAUSED;

    /**
     * Get status string.
     *
     * @return the string
     */
    public String getStatus(){
        return this.toString().toLowerCase();
    }
}
