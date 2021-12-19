package com.development.productioncenter.model.dao.mapper.impl;

import com.development.productioncenter.entity.Lesson;
import com.development.productioncenter.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.development.productioncenter.model.dao.ColumnName.*;

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
            lesson.getCourse().setId(resultSet.getLong(LESSON_ID));
            lesson.setWeekDay(resultSet.getString(LESSON_WEEK_DAY));
            lesson.setStartTime(resultSet.getTime(LESSON_START_TIME).toLocalTime());
            lesson.setDuration(resultSet.getInt(LESSON_DURATION));
            lessons.add(lesson);
        }
        return lessons;
    }
}
