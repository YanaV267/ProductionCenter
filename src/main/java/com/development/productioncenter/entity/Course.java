package com.development.productioncenter.entity;

import java.math.BigDecimal;

public class Course extends AbstractEntity {
    private String description;
    private Activity activity;
    private AgeGroup ageGroup;
    private User teacher;
    private BigDecimal lessonPrice;
    private int studentAmount;
    private CourseStatus courseStatus;

    public Course() {
        activity = new Activity();
        ageGroup = new AgeGroup();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public BigDecimal getLessonPrice() {
        return lessonPrice;
    }

    public void setLessonPrice(BigDecimal lessonPrice) {
        this.lessonPrice = lessonPrice;
    }

    public int getStudentAmount() {
        return studentAmount;
    }

    public void setStudentAmount(int studentAmount) {
        this.studentAmount = studentAmount;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
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
        Course course = (Course) o;
        if (description != null ? !description.equals(course.description) : course.description != null) {
            return false;
        }
        if (activity != null ? !activity.equals(course.activity) : course.activity != null) {
            return false;
        }
        if (ageGroup != null ? !ageGroup.equals(course.ageGroup) : course.ageGroup != null) {
            return false;
        }
        if (teacher != null ? !teacher.equals(course.teacher) : course.teacher != null) {
            return false;
        }
        if (lessonPrice != null ? !lessonPrice.equals(course.lessonPrice) : course.lessonPrice != null) {
            return false;
        }
        if (studentAmount != course.studentAmount) {
            return false;
        }
        return courseStatus != null ? courseStatus.equals(course.courseStatus) : course.courseStatus == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (description != null ? description.hashCode() : 0);
        result = result * 31 + (activity != null ? activity.hashCode() : 0);
        result = result * 31 + (ageGroup != null ? ageGroup.hashCode() : 0);
        result = result * 31 + (teacher != null ? teacher.hashCode() : 0);
        result = result * 31 + (lessonPrice != null ? lessonPrice.hashCode() : 0);
        result = result * 31 + Integer.hashCode(studentAmount);
        result = result * 31 + (courseStatus != null ? courseStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("description='").append(description);
        sb.append("', activity").append(activity);
        sb.append("', ageGroup").append(ageGroup);
        sb.append("', teacher").append(teacher);
        sb.append("', lessonPrice").append(lessonPrice);
        sb.append("', studentAmount").append(studentAmount);
        sb.append("', courseStatus").append(courseStatus);
        return sb.toString();
    }
}
