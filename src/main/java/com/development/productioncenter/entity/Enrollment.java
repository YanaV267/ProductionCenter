package com.development.productioncenter.entity;

import java.sql.Date;

public class Enrollment extends AbstractEntity {
    private User user;
    private Course course;
    private int lessonAmount;
    private Date reservationDateTime;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment() {
        user = new User();
        course = new Course();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getLessonAmount() {
        return lessonAmount;
    }

    public void setLessonAmount(int lessonAmount) {
        this.lessonAmount = lessonAmount;
    }

    public Date getReservationDateTime() {
        return reservationDateTime;
    }

    public void setReservationDateTime(Date reservationDateTime) {
        this.reservationDateTime = reservationDateTime;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

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
}
