package com.dev.productioncenter.model.dao;

import com.dev.productioncenter.model.dao.impl.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Dao provider.
 */
public class DaoProvider {
    private DaoProvider() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DaoProvider getInstance() {
        return DaoProviderHolder.instance;
    }

    /**
     * Gets activity dao.
     *
     * @param isTransaction the is transaction
     * @return the activity dao
     */
    public ActivityDao getActivityDao(boolean isTransaction) {
        return new ActivityDaoImpl(isTransaction);
    }

    /**
     * Gets age group dao.
     *
     * @param isTransaction the is transaction
     * @return the age group dao
     */
    public AgeGroupDao getAgeGroupDao(boolean isTransaction) {
        return new AgeGroupDaoImpl(isTransaction);
    }

    /**
     * Gets course dao.
     *
     * @param isTransaction the is transaction
     * @return the course dao
     */
    public CourseDao getCourseDao(boolean isTransaction) {
        return new CourseDaoImpl(isTransaction);
    }

    /**
     * Gets lesson dao.
     *
     * @param isTransaction the is transaction
     * @return the lesson dao
     */
    public LessonDao getLessonDao(boolean isTransaction) {
        return new LessonDaoImpl(isTransaction);
    }

    /**
     * Gets user dao.
     *
     * @param isTransaction the is transaction
     * @return the user dao
     */
    public UserDao getUserDao(boolean isTransaction) {
        return new UserDaoImpl(isTransaction);
    }

    private static class DaoProviderHolder {
        private static final DaoProvider instance = new DaoProvider();
    }
}
