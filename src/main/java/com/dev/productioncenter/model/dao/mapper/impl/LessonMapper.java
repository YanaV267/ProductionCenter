package com.dev.productioncenter.model.dao.mapper.impl;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.model.dao.ColumnName;
import com.dev.productioncenter.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonMapper implements Mapper<Lesson> {
    private static final LessonMapper INSTANCE = new LessonMapper();

    private LessonMapper() {
    }

    public static LessonMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Lesson> retrieve(ResultSet resultSet) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) {
            Lesson lesson = new Lesson();
            lesson.getCourse().setId(resultSet.getLong(ColumnName.LESSON_ID));
            lesson.setWeekDay(resultSet.getString(ColumnName.LESSON_WEEK_DAY));
            lesson.setStartTime(resultSet.getTime(ColumnName.LESSON_START_TIME).toLocalTime());
            lesson.setDuration(resultSet.getInt(ColumnName.LESSON_DURATION));
            lessons.add(lesson);
        }
        return lessons;
    }
}
