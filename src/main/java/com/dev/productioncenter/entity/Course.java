package com.dev.productioncenter.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Course extends AbstractEntity {
    private String description;
    private Activity activity;
    private AgeGroup ageGroup;
    private User teacher;
    private List<Lesson> lessons;
    private BigDecimal lessonPrice;
    private int studentAmount;
    private CourseStatus status;

    public Course() {
        activity = new Activity();
        ageGroup = new AgeGroup();
        lessons = new ArrayList<>();
    }

    public Course(long id) {
        super(id);
        activity = new Activity();
        ageGroup = new AgeGroup();
        lessons = new ArrayList<>();
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

    public List<Lesson> getLessons() {
        return List.copyOf(lessons);
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = List.copyOf(lessons);
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

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
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
        if (lessons != null ? !lessons.equals(course.lessons) : course.lessons != null) {
            return false;
        }
        if (lessonPrice != null ? !lessonPrice.equals(course.lessonPrice) : course.lessonPrice != null) {
            return false;
        }
        if (studentAmount != course.studentAmount) {
            return false;
        }
        return status != null ? status.equals(course.status) : course.status == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (description != null ? description.hashCode() : 0);
        result = result * 31 + (activity != null ? activity.hashCode() : 0);
        result = result * 31 + (ageGroup != null ? ageGroup.hashCode() : 0);
        result = result * 31 + (teacher != null ? teacher.hashCode() : 0);
        result = result * 31 + (lessons != null ? lessons.hashCode() : 0);
        result = result * 31 + (lessonPrice != null ? lessonPrice.hashCode() : 0);
        result = result * 31 + Integer.hashCode(studentAmount);
        result = result * 31 + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("description='").append(description);
        sb.append("', activity=").append(activity);
        sb.append("', ageGroup=").append(ageGroup);
        sb.append("', teacher=").append(teacher);
        sb.append("', lessons=").append(lessons);
        sb.append("', lessonPrice=").append(lessonPrice);
        sb.append("', studentAmount=").append(studentAmount);
        sb.append("', courseStatus=").append(status);
        return sb.toString();
    }

    public static class CourseBuilder {
        private final Course course;

        public CourseBuilder() {
            course = new Course();
        }

        public CourseBuilder setId(Long id) {
            course.setId(id);
            return this;
        }

        public CourseBuilder setDescription(String description) {
            course.description = description;
            return this;
        }

        public CourseBuilder setActivity(Activity activity) {
            course.activity = activity;
            return this;
        }

        public CourseBuilder setAgeGroup(AgeGroup ageGroup) {
            course.ageGroup = ageGroup;
            return this;
        }

        public CourseBuilder setTeacher(User teacher) {
            course.teacher = teacher;
            return this;
        }

        public CourseBuilder setLessonPrice(BigDecimal lessonPrice) {
            course.lessonPrice = lessonPrice;
            return this;
        }

        public CourseBuilder setStudentAmount(int studentAmount) {
            course.studentAmount = studentAmount;
            return this;
        }

        public CourseBuilder setCourseStatus(CourseStatus courseStatus) {
            course.status = courseStatus;
            return this;
        }

        public Course build() {
            return course;
        }
    }
}
