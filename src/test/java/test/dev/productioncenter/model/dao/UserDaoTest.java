package test.dev.productioncenter.model.dao;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.model.dao.impl.UserDaoImpl;
import com.dev.productioncenter.model.dao.mapper.impl.UserMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class UserDaoTest {
    private MockedStatic<UserMapper> userMapper;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserDaoImpl userDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
        userMapper = mockStatic(UserMapper.class);
    }

    @Test
    public void add() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        User user = new User();
        user.setPhoneNumber(new BigInteger("375297004933"));
        user.setUserRole(UserRole.USER);
        long expected = 5;
        long actual = userDao.add(user);
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void update() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        User user = new User();
        user.setPhoneNumber(new BigInteger("375299869531"));
        user.setUserRole(UserRole.USER);
        boolean actual = userDao.update(user);
        Assert.assertTrue(actual);
    }

    @Test
    public void delete() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        long courseId = 14;
        boolean actual = userDao.delete(courseId);
        Assert.assertFalse(actual);
    }

    @Test
    public void findAll() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        List<User> actual = userDao.findAll();
        Assert.assertFalse(actual.isEmpty());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void findById() {
        long userId = 5;
        Optional<User> actual = userDao.findById(userId);
        Assert.assertTrue(actual.isPresent());
    }

    @Test
    public void updateUserPassword() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        String password = "password";
        String login = "login";
        boolean actual = userDao.updateUserPassword(password, login);
        Assert.assertTrue(actual);
    }

    @Test
    public void findUserByLogin() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        String login = "sofi27";
        Optional<User> actual = userDao.findUserByLogin(login);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUserPassword() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        String login = "sofi27";
        Optional<String> actual = userDao.findUserPassword(login);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findTeacherByName() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        String surname = "Балышева";
        String name = "Мария";
        Optional<User> actual = userDao.findTeacherByName(surname, name);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUserByEmail() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        String email = "sofi2708@mail.ru";
        Optional<User> actual = userDao.findUserByEmail(email);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findUsersByFullNameStatus() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        int startElementNumber = 15;
        User user = new User();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserRole(UserRole.USER);
        List<User> actual = userDao.findUsersByFullNameStatus(user, startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUsersBySurnameStatus() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        int startElementNumber = 15;
        User user = new User();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserRole(UserRole.USER);
        List<User> actual = userDao.findUsersBySurnameStatus(user, startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUsersBySurname() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        int startElementNumber = 15;
        User user = new User();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserRole(UserRole.USER);
        List<User> actual = userDao.findUsersByFullNameStatus(user, startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUsersByFullName() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        int startElementNumber = 15;
        User user = new User();
        user.setUserRole(UserRole.USER);
        List<User> actual = userDao.findUsersByFullName(user, startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUsersByStatus() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        int startElementNumber = 45;
        User user = new User();
        user.setUserStatus(UserStatus.ACTIVE);
        user.setUserRole(UserRole.USER);
        List<User> actual = userDao.findUsersByStatus(user, startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUsersByRole() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        List<User> actual = userDao.findUsersByRole(UserRole.USER);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findUsersTeachers() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of(new User()));

        int startElementNumber = 30;
        List<User> actual = userDao.findUsersTeachers(startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findTeachersHoldingLessons() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        int startElementNumber = 30;
        List<User> actual = userDao.findTeachersHoldingLessons(startElementNumber);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void findTeachersHoldingLessonsBySurname() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        int startElementNumber = 30;
        String surname = "Кузнецов";
        List<User> actual = userDao.findTeachersHoldingLessonsBySurname(surname, startElementNumber);
        Assert.assertFalse(actual.isEmpty());
    }

    @Test
    public void findTeachersHoldingLessonsByFullName() throws DaoException, SQLException {
        userMapper.when(UserMapper::getInstance).thenReturn(mapper);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(mapper.retrieve(any())).thenReturn(List.of());

        int startElementNumber = 30;
        List<User> actual = userDao.findTeachersHoldingLessonsByFullName(new User(), startElementNumber);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void updateUserLogin() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        String currentLogin = "0987login";
        String newLogin = "login123";
        boolean actual = userDao.updateUserLogin(currentLogin, newLogin);
        Assert.assertTrue(actual);
    }

    @Test
    public void updateUserStatus() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        String login = "annieee";
        boolean actual = userDao.updateUserStatus(login, UserStatus.BLOCKED);
        Assert.assertTrue(actual);
    }

    @Test
    public void updateUserRole() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        String login = "annieee";
        boolean actual = userDao.updateUserRole(login, UserRole.TEACHER);
        Assert.assertFalse(actual);
    }

    @Test
    public void loadPicture() throws DaoException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        String login = "annieee";
        Optional<InputStream> actual = userDao.loadPicture(login);
        Assert.assertTrue(actual.isPresent());
    }
}
