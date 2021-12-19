package com.development.productioncenter.model.dao.mapper.impl;

import com.development.productioncenter.entity.*;
import com.development.productioncenter.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.development.productioncenter.model.dao.ColumnName.*;

public class CourseMapper implements Mapper<Course> {
    private static final CourseMapper INSTANCE = new CourseMapper();

    private CourseMapper() {
    }

    public static CourseMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Course> retrieve(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            Course course = new Course.CourseBuilder()
                    .setId(resultSet.getLong(COURSE_ID))
                    .setDescription(resultSet.getString(COURSE_DESCRIPTION))
                    .setTeacher(new User.UserBuilder()
                            .setSurname(resultSet.getString(USER_SURNAME))
                            .setName(resultSet.getString(USER_NAME))
                            .build())
                    .setActivity(new Activity.ActivityBuilder()
                            .setCategory(resultSet.getString(ACTIVITY_CATEGORY))
                            .setType(resultSet.getString(ACTIVITY_TYPE))
                            .build())
                    .setAgeGroup(new AgeGroup.AgeGroupBuilder()
                            .setMinAge(resultSet.getInt(AGE_GROUP_MIN_AGE))
                            .setMaxAge(resultSet.getInt(AGE_GROUP_MAX_AGE))
                            .build())
                    .setLessonPrice(resultSet.getBigDecimal(COURSE_LESSON_PRICE))
                    .setStudentAmount(resultSet.getInt(COURSE_STUDENT_AMOUNT))
                    .setCourseStatus(CourseStatus.valueOf(resultSet.getString(COURSE_STATUS).toUpperCase()))
                    .build();
            courses.add(course);
        }
        return courses;
    }
}