package com.dev.productioncenter.model.dao.mapper.impl;

import com.dev.productioncenter.entity.*;
import com.dev.productioncenter.model.dao.ColumnName;
import com.dev.productioncenter.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    .setId(resultSet.getLong(ColumnName.ENROLLMENT_ID))
                    .setUser(new User.UserBuilder()
                            .setSurname(resultSet.getString(ColumnName.USER_SURNAME))
                            .setName(resultSet.getString(ColumnName.USER_NAME))
                            .build())
                    .setCourse(new Course.CourseBuilder()
                            .setActivity(new Activity.ActivityBuilder()
                                    .setCategory(resultSet.getString(ColumnName.ACTIVITY_CATEGORY))
                                    .setType(resultSet.getString(ColumnName.ACTIVITY_TYPE))
                                    .build())
                            .build())
                    .setLessonAmount(resultSet.getInt(ColumnName.ENROLLMENT_LESSON_AMOUNT))
                    .setReservationDateTime(resultSet.getTimestamp(ColumnName.ENROLLMENT_RESERVATION_DATETIME).toLocalDateTime())
                    .setEnrollmentStatus(EnrollmentStatus.valueOf(resultSet.getString(ColumnName.ENROLLMENT_STATUS).toUpperCase()))
                    .build();
            enrollments.add(enrollment);
        }
        return enrollments;
    }
}
