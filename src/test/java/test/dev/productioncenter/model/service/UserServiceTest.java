package test.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.User;
import com.dev.productioncenter.entity.UserRole;
import com.dev.productioncenter.entity.UserStatus;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.DaoProvider;
import com.dev.productioncenter.model.dao.Transaction;
import com.dev.productioncenter.model.dao.UserDao;
import com.dev.productioncenter.model.service.UserService;
import com.dev.productioncenter.model.service.impl.UserServiceImpl;
import com.dev.productioncenter.util.PasswordEncoder;
import com.dev.productioncenter.util.PhoneNumberFormatter;
import com.dev.productioncenter.validator.UserValidator;
import com.dev.productioncenter.validator.impl.UserValidatorImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private MockedStatic<DaoProvider> daoProviderHolder;
    private MockedStatic<UserValidatorImpl> userValidator;
    private MockedStatic<Transaction> transactionMocked;
    private MockedStatic<PasswordEncoder> passwordEncoder;
    private MockedStatic<PhoneNumberFormatter> phoneNumberFormatter;
    @Mock
    private DaoProvider daoProvider;
    @Mock
    private Transaction transaction;
    @Mock
    private UserDao userDao;
    @Mock
    private UserValidator validator;
    @InjectMocks
    private UserService userService = UserServiceImpl.getInstance();

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeTest
    public void initStatic() {
        daoProviderHolder = mockStatic(DaoProvider.class);
        userValidator = mockStatic(UserValidatorImpl.class);
        transactionMocked = mockStatic(Transaction.class);
        passwordEncoder = mockStatic(PasswordEncoder.class);
        phoneNumberFormatter = mockStatic(PhoneNumberFormatter.class);
    }

    @Test(dataProvider = "userLoginPassword")
    public void findUser(String login, String password) throws ServiceException, DaoException {
        userValidator.when(UserValidatorImpl::getInstance).thenReturn(validator);
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);
        passwordEncoder.when(() -> PasswordEncoder.encode(anyString())).thenReturn(Optional.empty());

        when(validator.checkLogin(anyString())).thenReturn(true);
        when(validator.checkPassword(anyString())).thenReturn(true);
        when(userDao.findUserByLogin(anyString())).thenReturn(Optional.empty());
        when(userDao.findUserPassword(anyString())).thenReturn(Optional.of(""));
        doNothing().when(userDao).closeConnection();

        Optional<User> user = userService.findUser(login, password);
        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void findUser() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findUserByLogin(anyString())).thenReturn(Optional.empty());
        doNothing().when(userDao).closeConnection();

        String login = "login";
        Optional<User> user = userService.findUser(login);
        Assert.assertTrue(user.isPresent());
    }

    @Test(dataProvider = "userFullNameRole")
    public void findUser(String surname, String name, UserRole userRole) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findTeacherByName(anyString(), anyString())).thenReturn(Optional.of(new User()));
        doNothing().when(userDao).closeConnection();

        Optional<User> user = userService.findUser(surname, name, userRole);
        Assert.assertTrue(user.isPresent());
    }

    @Test
    public void findUsers() throws ServiceException, DaoException {
        phoneNumberFormatter.when(() -> PhoneNumberFormatter.format(BigInteger.valueOf(anyLong()))).thenReturn("");
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findUsersByRole(any(UserRole.class))).thenReturn(List.of(new User()));
        doNothing().when(userDao).closeConnection();

        int page = 1;
        Map<User, String> actual = userService.findUsers(UserRole.USER, page);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test(dataProvider = "userSearchData")
    public void findUsers(Map<String, String> userData) throws ServiceException, DaoException {
        phoneNumberFormatter.when(() -> PhoneNumberFormatter.format(BigInteger.valueOf(anyLong()))).thenReturn("");
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findUsersByFullNameStatus(any(User.class), anyInt())).thenReturn(List.of(new User()));
        when(userDao.findUsersByFullName(any(User.class), anyInt())).thenReturn(List.of());
        when(userDao.findUsersBySurnameStatus(any(User.class), anyInt())).thenReturn(List.of(new User()));
        when(userDao.findUsersByStatus(any(User.class), anyInt())).thenReturn(List.of());
        when(userDao.findUsersBySurname(any(User.class), anyInt())).thenReturn(List.of());
        doNothing().when(userDao).closeConnection();

        Map<User, String> expected = Map.of(new User(), "+375(44)278-5513");
        int page = 1;
        Map<User, String> actual = userService.findUsers(userData, page);
        Assert.assertEquals(actual.size(), expected.size());
    }

    @Test
    public void findUsersTeachers() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);
        phoneNumberFormatter.when(() -> PhoneNumberFormatter.format(BigInteger.valueOf(anyLong()))).thenReturn("");

        when(userDao.findUsersTeachers(anyInt())).thenReturn(List.of(new User()));
        doNothing().when(userDao).closeConnection();

        int page = 2;
        Map<User, String> expected = Map.of(new User(), "+375(29)238-43-23");
        Map<User, String> actual = userService.findUsersTeachers(page);
        Assert.assertEquals(actual, expected);
    }

    @Test(dataProvider = "userSearchData")
    public void findTeachers(Map<String, String> teacherData) throws ServiceException, DaoException {
        phoneNumberFormatter.when(() -> PhoneNumberFormatter.format(BigInteger.valueOf(anyLong()))).thenReturn("");
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findTeachersHoldingLessonsByFullName(any(User.class), anyInt())).thenReturn(List.of(new User()));
        when(userDao.findTeachersHoldingLessonsBySurname(anyString(), anyInt())).thenReturn(List.of());
        when(userDao.findTeachersHoldingLessons(anyInt())).thenReturn(List.of(new User()));
        when(userDao.findUsersByFullName(any(User.class), anyInt())).thenReturn(List.of());
        when(userDao.findUsersBySurname(any(User.class), anyInt())).thenReturn(List.of());
        doNothing().when(userDao).closeConnection();

        Map<User, String> expected = Map.of(new User(), "+375(44)278-5513");
        int page = 1;
        Map<User, String> actual = userService.findTeachers(teacherData, page);
        Assert.assertEquals(actual.size(), expected.size());
    }

    @Test
    public void isLoginOccupied() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findUserByLogin(anyString())).thenReturn(Optional.empty());
        doNothing().when(userDao).closeConnection();

        String login = "login";
        boolean actual = userService.isLoginOccupied(login);
        Assert.assertFalse(actual);
    }

    @Test
    public void isEmailOccupied() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findUserByEmail(anyString())).thenReturn(Optional.of(new User()));
        doNothing().when(userDao).closeConnection();

        String email = "email19@gmail.com";
        boolean actual = userService.isEmailOccupied(email);
        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "userData")
    public void registerUser(Map<String, String> userData) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        userValidator.when(UserValidatorImpl::getInstance).thenReturn(validator);
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        when(daoProvider.getUserDao(true)).thenReturn(userDao);

        when(validator.checkUserData(anyMap())).thenReturn(true);
        when(userDao.add(any(User.class))).thenReturn(Long.valueOf(1));
        when(userDao.updateUserPassword(anyString(), anyString())).thenReturn(true);
        doNothing().when(transaction).begin(userDao);
        doNothing().when(transaction).commit();
        doNothing().when(transaction).end();

        boolean actual = userService.registerUser(userData);
        Assert.assertFalse(actual);
    }

    @Test
    public void checkRoles() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.findUserByLogin(anyString())).thenReturn(Optional.of(new User()));
        doNothing().when(userDao).closeConnection();

        Map<String, UserRole> usersRoles = Map.of("login1", UserRole.USER, "login2", UserRole.TEACHER);
        boolean actual = userService.checkRoles(usersRoles);
        Assert.assertTrue(actual);
    }

    @Test
    public void updateStatuses() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.updateUserRole(anyString(), any(UserRole.class))).thenReturn(true);
        doNothing().when(userDao).closeConnection();

        Map<String, UserStatus> usersStatuses = Map.of("login1", UserStatus.ACTIVE, "login2", UserStatus.ACTIVE);
        boolean actual = userService.updateStatuses(usersStatuses);
        Assert.assertTrue(actual);
    }

    @Test
    public void updateRoles() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.updateUserRole(anyString(), any(UserRole.class))).thenReturn(true);
        doNothing().when(userDao).closeConnection();

        Map<String, UserRole> usersRoles = Map.of("login1", UserRole.USER, "login2", UserRole.TEACHER);
        boolean actual = userService.updateRoles(usersRoles);
        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "userData")
    public void updateUserLogin(Map<String, String> userData) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        userValidator.when(UserValidatorImpl::getInstance).thenReturn(validator);
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        when(daoProvider.getUserDao(true)).thenReturn(userDao);

        when(validator.checkLogin(anyString())).thenReturn(true);
        when(userDao.findUserPassword(anyString())).thenReturn(Optional.of(""));
        when(userDao.updateUserLogin(anyString(), anyString())).thenReturn(true);
        doNothing().when(transaction).begin(userDao);
        doNothing().when(transaction).rollback();

        boolean actual = userService.updateUserLogin(userData);
        Assert.assertFalse(actual);
    }

    @Test(dataProvider = "userData")
    public void updateUserAccountData(Map<String, String> userData) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        userValidator.when(UserValidatorImpl::getInstance).thenReturn(validator);
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        when(daoProvider.getUserDao(true)).thenReturn(userDao);

        when(validator.checkLogin(anyString())).thenReturn(true);
        when(validator.checkUserPersonalData(anyMap())).thenReturn(true);
        when(userDao.update(any(User.class))).thenReturn(true);
        when(userDao.updateUserPassword(anyString(), anyString())).thenReturn(true);
        doNothing().when(transaction).begin(userDao);
        doNothing().when(transaction).commit();
        doNothing().when(transaction).rollback();
        doNothing().when(transaction).end();

        boolean actual = userService.updateUserAccountData(userData);
        Assert.assertFalse(actual);
    }

    @Test
    public void updatePicture() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.updatePicture(anyString(), any())).thenReturn(true);
        doNothing().when(userDao).closeConnection();

        String login = "login123";
        boolean actual = userService.updatePicture(login, InputStream.nullInputStream());
        Assert.assertTrue(actual);
    }

    @Test
    public void loadPicture() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getUserDao(false)).thenReturn(userDao);

        when(userDao.loadPicture(anyString())).thenReturn(Optional.empty());
        doNothing().when(userDao).closeConnection();

        String login = "login123";
        Optional<String> actual = userService.loadPicture(login);
        Assert.assertTrue(actual.isPresent());
    }

    @DataProvider(name = "userLoginPassword")
    public Object[][] getUserLoginPassword() {
        return new Object[][]{
                {"sofi27", "password"}, {"marishka17", "qwerty1234"}
        };
    }

    @DataProvider(name = "userFullNameRole")
    public Object[][] getUserFullNameRole() {
        return new Object[][]{
                {"Лещенко", "Ирина", UserRole.TEACHER},
                {"Лесюкова", "Марина", UserRole.USER}
        };
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][]{
                {new HashMap<>(Map.of(LOGIN, "fima1992", PASSWORD, "password", REPEATED_PASSWORD, "password",
                        NEW_PASSWORD, "passpass", SURNAME, "Фомичёв", NAME, "Михаил",
                        EMAIL, "mishafomichev@mail.ru", PHONE_NUMBER, "+375(29)109-88-02"))},
                {new HashMap<>(Map.of(LOGIN, "sunnyday", PASSWORD, "1212129090", REPEATED_PASSWORD, "passpass",
                        NEW_PASSWORD, "passpass", SURNAME, "Титова", NAME, "Кира",
                        EMAIL, "kiratit@gmail.com", PHONE_NUMBER, "+375(44)699-03-41"))}
        };
    }

    @DataProvider(name = "userSearchData")
    public Object[][] getUserSearchData() {
        return new Object[][]{
                {Map.of(SURNAME, "Фомичёв", STATUS, UserStatus.ACTIVE.getStatus())},
                {Map.of(SURNAME, "Титова", NAME, "Кира", STATUS, UserStatus.ACTIVE.getStatus())},
                {Map.of(STATUS, UserStatus.ACTIVE.getStatus())},
        };
    }
}
