package com.dev.productioncenter.model.dao.mapper.impl;

import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.model.dao.mapper.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.dev.productioncenter.model.dao.ColumnName.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Lesson mapper.
 */
public class LessonMapper implements Mapper<Lesson> {
    private static final LessonMapper instance = new LessonMapper();

    private LessonMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static LessonMapper getInstance() {
        return instance;
    }

    @Override
    public List<Lesson> retrieve(ResultSet resultSet) throws SQLException {
        List<Lesson> lessons = new ArrayList<>();
        while (resultSet.next()) {
            Lesson lesson = new Lesson();
            lesson.setId(resultSet.getLong(LESSON_ID));
            lesson.setWeekDay(resultSet.getString(LESSON_WEEK_DAY));
            lesson.setStartTime(resultSet.getTime(LESSON_START_TIME).toLocalTime());
            lesson.setDuration(resultSet.getInt(LESSON_DURATION));
            lessons.add(lesson);
        }
        return lessons;
    }
}
