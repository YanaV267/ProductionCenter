package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;
import org.testng.annotations.Test;

import java.io.InputStream;

public class UserDao {
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
    public void updateUserPassword(String password, String login) throws DaoException {
    }

    @Test
    public void findUserByLogin(String login) throws DaoException {
    }

    @Test
    public void findUserPassword(String login) throws DaoException {
    }

    @Test
    public void findTeacherByName(String surname, String name) throws DaoException {
    }

    @Test
    public void findUserByEmail(String email) throws DaoException {
    }

    @Test
    public void findUsersByFullNameStatus(User user, int startElementNumber) throws DaoException {
    }

    @Test
    public void findUsersBySurnameStatus(User user, int startElementNumber) throws DaoException {
    }

    @Test
    public void findUsersBySurname(User user, int startElementNumber) throws DaoException {
    }

    @Test
    public void findUsersByFullName(User user, int startElementNumber) throws DaoException {
    }

    @Test
    public void findUsersByStatus(User user, int startElementNumber) throws DaoException {
    }

    @Test
    public void findUsersByRole(UserRole role) throws DaoException {
    }

    @Test
    public void findUsersByRole(UserRole role, int startElementNumber) throws DaoException {
    }

    @Test
    public void findUsersTeachers(int startElementNumber) throws DaoException {
    }

    @Test
    public void findTeachersHoldingLessons(int startElementNumber) throws DaoException {
    }

    @Test
    public void findTeachersHoldingLessonsBySurname(String surname, int startElementNumber) throws DaoException {
    }

    @Test
    public void findTeachersHoldingLessonsByFullName(User teacher, int startElementNumber) throws DaoException {
    }

    @Test
    public void updateUserLogin(String currentLogin, String newLogin) throws DaoException {
    }

    @Test
    public void updateUserStatus(String login, UserStatus currentStatus) throws DaoException {
    }

    @Test
    public void updateUserRole(String login, UserRole currentRole) throws DaoException {
    }

    @Test
    public void updatePicture(String login, InputStream pictureStream) throws DaoException {
    }

    @Test
    public void loadPicture(String login) throws DaoException {
    }
}
