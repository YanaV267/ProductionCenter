package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.entity.AgeGroup;
import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;
import org.testng.annotations.Test;

public class CourseDao {
    @Test
    public void add() throws DaoException {
    }

    @Test
    public void update() throws DaoException {
    }

    @Test
    public void delete() throws DaoException {
    }

    @Test
    public void findAll() throws DaoException {
    }

    @Test
    public void findById() throws DaoException {
    }

    @Test
    public void findAll(int startElementNumber) throws DaoException {
    }

    @Test
    public void updateCourseStudentAmount(long id, int studentAmount) throws DaoException {
    }

    @Test
    public void findCourseByTeacher(User teacher) throws DaoException {
    }

    @Test
    public void findCourseByAgeGroup(AgeGroup ageGroup) throws DaoException {
    }

    @Test
    public void findCourseByActivity(Activity activity, int startElementNumber) throws DaoException {
    }

    @Test
    public void findCourseByActivityCategory(Activity activity, int startElementNumber) throws DaoException {
    }

    @Test
    public void findCourseByActivityType(Activity activity, int startElementNumber) throws DaoException {
    }

    @Test
    public void findCourseByActivityWeekday(Activity activity, String weekday, int startElementNumber) throws DaoException {
    }

    @Test
    public void findCourseByWeekday(String weekday) throws DaoException {
    }

    @Test
    public void findAvailableCourses(int startElementNumber) throws DaoException {
    }

    @Test
    public void findCoursesAllActivities() throws DaoException {
    }

    @Test
    public void findCoursesAvailableActivities() throws DaoException {
    }
}