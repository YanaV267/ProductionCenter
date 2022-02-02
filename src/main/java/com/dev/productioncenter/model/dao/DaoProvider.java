package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.model.dao.impl.*;

public class DaoProvider {
    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return DaoProviderHolder.instance;
    }

    public ActivityDao getActivityDao(boolean isTransaction) {
        return new ActivityDaoImpl(isTransaction);
    }

    public AgeGroupDao getAgeGroupDao(boolean isTransaction) {
        return new AgeGroupDaoImpl(isTransaction);
    }

    public CourseDao getCourseDao(boolean isTransaction) {
        return new CourseDaoImpl(isTransaction);
    }

    public LessonDao getLessonDao(boolean isTransaction) {
        return new LessonDaoImpl(isTransaction);
    }

    public UserDao getUserDao(boolean isTransaction) {
        return new UserDaoImpl(isTransaction);
    }

    private static class DaoProviderHolder {
        private static final DaoProvider instance = new DaoProvider();
    }
}
