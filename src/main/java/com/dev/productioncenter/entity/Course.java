package com.dev.productioncenter.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YanaV
 * The type Course.
 * @project Production Center
 */
public class Course extends AbstractEntity implements Cloneable {
    private String description;
    private Activity activity;
    private AgeGroup ageGroup;
    private User teacher;
    private List<Lesson> lessons;
    private BigDecimal lessonPrice;
    private int studentAmount;
    private CourseStatus status;

    /**
     * Instantiates a new Course.
     */
    public Course() {
        activity = new Activity();
        ageGroup = new AgeGroup();
        lessons = new ArrayList<>();
    }

    /**
     * Instantiates a new Course.
     *
     * @param id the id
     */
    public Course(long id) {
        super(id);
        activity = new Activity();
        ageGroup = new AgeGroup();
        lessons = new ArrayList<>();
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets activity.
     *
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Sets activity.
     *
     * @param activity the activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * Gets age group.
     *
     * @return the age group
     */
    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    /**
     * Sets age group.
     *
     * @param ageGroup the age group
     */
    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    /**
     * Gets teacher.
     *
     * @return the teacher
     */
    public User getTeacher() {
        return teacher;
    }

    /**
     * Sets teacher.
     *
     * @param teacher the teacher
     */
    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    /**
     * Gets lessons.
     *
     * @return the lessons
     */
    public List<Lesson> getLessons() {
        return List.copyOf(lessons);
    }

    /**
     * Sets lessons.
     *
     * @param lessons the lessons
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons = List.copyOf(lessons);
    }

    /**
     * Gets lesson price.
     *
     * @return the lesson price
     */
    public BigDecimal getLessonPrice() {
        return lessonPrice;
    }

    /**
     * Sets lesson price.
     *
     * @param lessonPrice the lesson price
     */
    public void setLessonPrice(BigDecimal lessonPrice) {
        this.lessonPrice = lessonPrice;
    }

    /**
     * Gets student amount.
     *
     * @return the student amount
     */
    public int getStudentAmount() {
        return studentAmount;
    }

    /**
     * Sets student amount.
     *
     * @param studentAmount the student amount
     */
    public void setStudentAmount(int studentAmount) {
        this.studentAmount = studentAmount;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public CourseStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
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

    @Override
    public Course clone() throws CloneNotSupportedException {
        Course course = (Course) super.clone();
        course.setDescription(description);
        course.setActivity(activity.clone());
        course.setAgeGroup(ageGroup.clone());
        course.setTeacher(teacher.clone());
        course.setLessons(List.copyOf(lessons));
        course.setLessonPrice(lessonPrice);
        course.setStudentAmount(studentAmount);
        course.setStatus(status);
        return course;
    }

    /**
     * The type Course builder.
     */
    public static class CourseBuilder {
        private final Course course;

        /**
         * Instantiates a new Course builder.
         */
        public CourseBuilder() {
            course = new Course();
        }

        /**
         * Sets id.
         *
         * @param id the id
         * @return the id
         */
        public CourseBuilder setId(Long id) {
            course.setId(id);
            return this;
        }

        /**
         * Sets description.
         *
         * @param description the description
         * @return the description
         */
        public CourseBuilder setDescription(String description) {
            course.description = description;
            return this;
        }

        /**
         * Sets activity.
         *
         * @param activity the activity
         * @return the activity
         */
        public CourseBuilder setActivity(Activity activity) {
            course.activity = activity;
            return this;
        }

        /**
         * Sets age group.
         *
         * @param ageGroup the age group
         * @return the age group
         */
        public CourseBuilder setAgeGroup(AgeGroup ageGroup) {
            course.ageGroup = ageGroup;
            return this;
        }

        /**
         * Sets teacher.
         *
         * @param teacher the teacher
         * @return the teacher
         */
        public CourseBuilder setTeacher(User teacher) {
            course.teacher = teacher;
            return this;
        }

        /**
         * Sets lesson price.
         *
         * @param lessonPrice the lesson price
         * @return the lesson price
         */
        public CourseBuilder setLessonPrice(BigDecimal lessonPrice) {
            course.lessonPrice = lessonPrice;
            return this;
        }

        /**
         * Sets student amount.
         *
         * @param studentAmount the student amount
         * @return the student amount
         */
        public CourseBuilder setStudentAmount(int studentAmount) {
            course.studentAmount = studentAmount;
            return this;
        }

        /**
         * Sets course status.
         *
         * @param courseStatus the course status
         * @return the course status
         */
        public CourseBuilder setCourseStatus(CourseStatus courseStatus) {
            course.status = courseStatus;
            return this;
        }

        /**
         * Build course.
         *
         * @return the course
         */
        public Course build() {
            return course;
        }
    }
}
