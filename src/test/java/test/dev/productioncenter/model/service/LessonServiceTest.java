package test.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Course;
import com.dev.productioncenter.entity.Lesson;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.DaoProvider;
import com.dev.productioncenter.model.dao.LessonDao;
import com.dev.productioncenter.model.dao.Transaction;
import com.dev.productioncenter.model.service.LessonService;
import com.dev.productioncenter.model.service.impl.LessonServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.dev.productioncenter.controller.command.RequestParameter.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LessonServiceTest {
    private MockedStatic<DaoProvider> daoProviderHolder;
    private MockedStatic<Transaction> transactionMocked;
    @Mock
    private DaoProvider daoProvider;
    @Mock
    private Transaction transaction;
    @Mock
    private LessonDao lessonDao;
    @InjectMocks
    private LessonService lessonService = LessonServiceImpl.getInstance();

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeTest
    public void initStatic() {
        daoProviderHolder = mockStatic(DaoProvider.class);
        transactionMocked = mockStatic(Transaction.class);
    }

    @Test
    public void addLessons() throws ServiceException, DaoException {
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getLessonDao(true)).thenReturn(lessonDao);

        when(lessonDao.add(any(Lesson.class))).thenReturn(Long.valueOf(1));
        doNothing().when(transaction).begin(lessonDao);

        Map<String, String> lessonData = Map.of(WEEKDAYS, "пн, ср, чт", TIME, "15:30", DURATION, "45");
        long courseId = 4;
        boolean actual = lessonService.addLessons(lessonData, courseId);
        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "lessonData")
    public void updateLessons(Map<String, String> lessonData) throws ServiceException, DaoException {
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getLessonDao(true)).thenReturn(lessonDao);

        when(lessonDao.findLessonsByCourseWeekDay(anyString(), anyLong())).thenReturn(Optional.empty());
        when(lessonDao.add(any(Lesson.class))).thenReturn(Long.valueOf(1));
        when(lessonDao.update(any(Lesson.class))).thenReturn(true);
        doNothing().when(transaction).begin(lessonDao);

        long courseId = 2;
        boolean actual = lessonService.addLessons(lessonData, courseId);
        Assert.assertTrue(actual);
    }

    @Test(dataProvider = "lessons")
    public void findLessons(List<Lesson> lessons) throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getLessonDao(false)).thenReturn(lessonDao);

        when(lessonDao.findLessonsByCourse(anyLong())).thenReturn(lessons);
        doNothing().when(lessonDao).closeConnection();

        long courseId = 7;
        List<Lesson> expected = List.of(new Lesson());
        List<Lesson> actual = lessonService.findLessons(courseId);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "lessons")
    public Object[][] getLessons() {
        List<Lesson> lessons = new ArrayList<>();
        String[] weekdays = new String[]{"пн", "чт", "пт"};
        for (int i = 0; i < 3; i++) {
            Lesson lesson = new Lesson();
            lesson.setCourse(new Course(i + 1));
            lesson.setWeekDay(weekdays[i]);
            lesson.setStartTime(LocalTime.parse("17:00"));
            lesson.setDuration(30);
            lessons.add(lesson);
        }
        return new Object[][]{{lessons}};
    }

    @DataProvider(name = "lessonData")
    public Object[][] getLessonData() {
        return new Object[][]{
                {Map.of(WEEKDAYS, "пн, ср, чт", TIME, "15:30", DURATION, "45")},
                {Map.of(WEEKDAYS, "вт", TIME, "18:45", DURATION, "60")}
        };
    }
}
