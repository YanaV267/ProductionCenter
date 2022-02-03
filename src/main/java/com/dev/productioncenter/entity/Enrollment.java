package com.dev.productioncenter.entity;

import java.time.LocalDateTime;

/**
 * @project Production Center
 * @author YanaV
 * The type Enrollment.
 */
public class Enrollment extends AbstractEntity {
    private User user;
    private Course course;
    private int lessonAmount;
    private LocalDateTime reservationDateTime;
    private EnrollmentStatus enrollmentStatus;

    /**
     * Instantiates a new Enrollment.
     */
    public Enrollment() {
        user = new User();
        course = new Course();
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets course.
     *
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Sets course.
     *
     * @param course the course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets lesson amount.
     *
     * @return the lesson amount
     */
    public int getLessonAmount() {
        return lessonAmount;
    }

    /**
     * Sets lesson amount.
     *
     * @param lessonAmount the lesson amount
     */
    public void setLessonAmount(int lessonAmount) {
        this.lessonAmount = lessonAmount;
    }

    /**
     * Gets reservation date time.
     *
     * @return the reservation date time
     */
    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }

    /**
     * Sets reservation date time.
     *
     * @param reservationDateTime the reservation date time
     */
    public void setReservationDateTime(LocalDateTime reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    /**
     * Gets enrollment status.
     *
     * @return the enrollment status
     */
    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    /**
     * Sets enrollment status.
     *
     * @param enrollmentStatus the enrollment status
     */
    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o)) {
            return false;
        }
        Enrollment enrollment = (Enrollment) o;
        if (user != null ? !user.equals(enrollment.user) : enrollment.user != null) {
            return false;
        }
        if (course != null ? !course.equals(enrollment.course) : enrollment.course != null) {
            return false;
        }
        if (lessonAmount != enrollment.lessonAmount) {
            return false;
        }
        if (reservationDateTime != null ? !reservationDateTime.equals(enrollment.reservationDateTime) : enrollment.reservationDateTime != null) {
            return false;
        }
        return enrollmentStatus != null ? enrollmentStatus.equals(enrollment.enrollmentStatus) : enrollment.enrollmentStatus == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (user != null ? user.hashCode() : 0);
        result = result * 31 + (course != null ? course.hashCode() : 0);
        result = result * 31 + Integer.hashCode(lessonAmount);
        result = result * 31 + (reservationDateTime != null ? reservationDateTime.hashCode() : 0);
        result = result * 31 + (enrollmentStatus != null ? enrollmentStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("user=").append(user);
        sb.append(", course=").append(course);
        sb.append(", lessonAmount=").append(lessonAmount);
        sb.append(", reservationDateTime=").append(reservationDateTime);
        sb.append(", enrollmentStatus=").append(enrollmentStatus);
        return sb.toString();
    }

    /**
     * The type Enrollment builder.
     */
    public static class EnrollmentBuilder {
        private final Enrollment enrollment;

        /**
         * Instantiates a new Enrollment builder.
         */
        public EnrollmentBuilder() {
            enrollment = new Enrollment();
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public EnrollmentBuilder setId(Long id) {
            enrollment.setId(id);
            return this;
        }

        /**
         * Sets user.
         *
         * @param user the user
         * @return the user
         */
        public EnrollmentBuilder setUser(User user) {
            enrollment.user = user;
            return this;
        }

        /**
         * Sets course.
         *
         * @param course the course
         * @return the course
         */
        public EnrollmentBuilder setCourse(Course course) {
            enrollment.course = course;
            return this;
        }

        /**
         * Sets lesson amount.
         *
         * @param lessonAmount the lesson amount
         * @return the lesson amount
         */
        public EnrollmentBuilder setLessonAmount(int lessonAmount) {
            enrollment.lessonAmount = lessonAmount;
            return this;
        }

        /**
         * Sets reservation date time.
         *
         * @param reservationDateTime the reservation date time
         * @return the reservation date time
         */
        public EnrollmentBuilder setReservationDateTime(LocalDateTime reservationDateTime) {
            enrollment.reservationDateTime = reservationDateTime;
            return this;
        }

        /**
         * Sets enrollment status.
         *
         * @param enrollmentStatus the enrollment status
         * @return the enrollment status
         */
        public EnrollmentBuilder setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
            enrollment.enrollmentStatus = enrollmentStatus;
            return this;
        }

        /**
         * Build enrollment.
         *
         * @return the enrollment
         */
        public Enrollment build() {
            return enrollment;
        }
    }
}
