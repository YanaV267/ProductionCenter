package com.dev.productioncenter.entity;

import java.time.LocalTime;

public class Lesson extends AbstractEntity {
    private Course course;
    private String weekDay;
    private LocalTime startTime;
    private int duration;

    public Lesson() {

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        Lesson lesson = (Lesson) o;
        if (course != null ? !course.equals(lesson.course) : lesson.course != null) {
            return false;
        }
        if (weekDay != null ? !weekDay.equals(lesson.weekDay) : lesson.weekDay != null) {
            return false;
        }
        if (duration != lesson.duration) {
            return false;
        }
        return startTime != null ? startTime.equals(lesson.startTime) : lesson.startTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (course != null ? course.hashCode() : 0);
        result = result * 31 + (weekDay != null ? weekDay.hashCode() : 0);
        result = result * 31 + (startTime != null ? startTime.hashCode() : 0);
        result = result * 31 + Integer.hashCode(duration);
        return result;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "course=" + course +
                ", weekDay='" + weekDay + '\'' +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
