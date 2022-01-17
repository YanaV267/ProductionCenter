package com.dev.productioncenter.model.dao.mapper.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.model.dao.ColumnName;
import com.dev.productioncenter.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseMapper implements Mapper<Course> {
    private static final CourseMapper instance = new CourseMapper();

    private CourseMapper() {
    }

    public static CourseMapper getInstance() {
        return instance;
    }

    @Override
    public List<Course> retrieve(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            Course course = new Course.CourseBuilder()
                    .setId(resultSet.getLong(ColumnName.COURSE_ID))
                    .setDescription(resultSet.getString(ColumnName.COURSE_DESCRIPTION))
                    .setTeacher(new User.UserBuilder()
                            .setSurname(resultSet.getString(ColumnName.USER_SURNAME))
                            .setName(resultSet.getString(ColumnName.USER_NAME))
                            .build())
                    .setActivity(new Activity.ActivityBuilder()
                            .setCategory(resultSet.getString(ColumnName.ACTIVITY_CATEGORY))
                            .setType(resultSet.getString(ColumnName.ACTIVITY_TYPE))
                            .build())
                    .setAgeGroup(new AgeGroup.AgeGroupBuilder()
                            .setMinAge(resultSet.getInt(ColumnName.AGE_GROUP_MIN_AGE))
                            .setMaxAge(resultSet.getInt(ColumnName.AGE_GROUP_MAX_AGE))
                            .build())
                    .setLessonPrice(resultSet.getBigDecimal(ColumnName.COURSE_LESSON_PRICE))
                    .setStudentAmount(resultSet.getInt(ColumnName.COURSE_STUDENT_AMOUNT))
                    .setCourseStatus(CourseStatus.valueOf(resultSet.getString(ColumnName.COURSE_STATUS).toUpperCase()))
                    .build();
            courses.add(course);
        }
        return courses;
    }
}
