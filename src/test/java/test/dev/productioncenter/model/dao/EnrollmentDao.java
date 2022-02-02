package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Enrollment;
import com.dev.productioncenter.entity.EnrollmentStatus;
import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.exception.DaoException;
import org.testng.annotations.Test;

public class EnrollmentDao {
    @Test
    public void updateEnrollmentStatus(Enrollment enrollment) throws DaoException {
    }

    @Test
    public void findEnrollments(int startElementNumber) throws DaoException {
    }

    @Test
    public void findEnrollmentsByUser(User user, int startElementNumber) throws DaoException {
    }

    @Test
    public void findEnrollmentsByUser(User user) throws DaoException {
    }

    @Test
    public void findEnrollmentsByCourse(Course course, int startElementNumber) throws DaoException {
    }

    @Test
    public void findEnrollmentsByCourseUser(User user, Course course) throws DaoException {
    }

    @Test
    public void findEnrollmentsByStatus(EnrollmentStatus status) throws DaoException {
    }

    @Test
    public void findExpiredEnrollments() throws DaoException {
    }
}
