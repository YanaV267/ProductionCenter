package com.dev.productioncenter.model.dao.mapper.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dev.productioncenter.controller.command.ColumnName.*;

public class EnrollmentMapper implements Mapper<Enrollment> {
    private static final EnrollmentMapper instance = new EnrollmentMapper();

    private EnrollmentMapper() {
    }

    public static EnrollmentMapper getInstance() {
        return instance;
    }

    @Override
    public List<Enrollment> retrieve(ResultSet resultSet) throws SQLException {
        List<Enrollment> enrollments = new ArrayList<>();
        while (resultSet.next()) {
            Enrollment enrollment = new Enrollment.EnrollmentBuilder()
                    .setId(resultSet.getLong(ENROLLMENT_ID))
                    .setUser(new User.UserBuilder()
                            .setSurname(resultSet.getString(ENROLLMENT_USER_SURNAME))
                            .setName(resultSet.getString(ENROLLMENT_USER_NAME))
                            .build())
                    .setCourse(new Course.CourseBuilder()
                            .setId(resultSet.getLong(ENROLLMENT_COURSE_ID))
                            .setTeacher(new User.UserBuilder()
                                    .setSurname(resultSet.getString(ENROLLMENT_TEACHER_SURNAME))
                                    .setName(resultSet.getString(ENROLLMENT_TEACHER_NAME))
                                    .build())
                            .setActivity(new Activity.ActivityBuilder()
                                    .setCategory(resultSet.getString(ACTIVITY_CATEGORY))
                                    .setType(resultSet.getString(ACTIVITY_TYPE))
                                    .build())
                            .setLessonPrice(resultSet.getBigDecimal(COURSE_LESSON_PRICE))
                            .build())
                    .setLessonAmount(resultSet.getInt(ENROLLMENT_LESSON_AMOUNT))
                    .setReservationDateTime(resultSet.getTimestamp(ENROLLMENT_RESERVATION_DATETIME).toLocalDateTime())
                    .setEnrollmentStatus(EnrollmentStatus.valueOf(resultSet.getString(ENROLLMENT_STATUS).toUpperCase()))
                    .build();
            enrollments.add(enrollment);
        }
        return enrollments;
    }
}
