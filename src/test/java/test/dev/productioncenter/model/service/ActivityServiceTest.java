package test.dev.productioncenter.model.service;

import com.dev.productioncenter.entity.Activity;
import com.dev.productioncenter.exception.DaoException;
import com.dev.productioncenter.exception.ServiceException;
import com.dev.productioncenter.model.dao.DaoProvider;
import com.dev.productioncenter.model.dao.impl.ActivityDaoImpl;
import com.dev.productioncenter.model.service.ActivityService;
import com.dev.productioncenter.model.service.impl.ActivityServiceImpl;
import com.dev.productioncenter.validator.CourseValidator;
import com.dev.productioncenter.validator.impl.CourseValidatorImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.dev.productioncenter.controller.command.RequestParameter.CATEGORY;
import static com.dev.productioncenter.controller.command.RequestParameter.TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class ActivityServiceTest {
    private MockedStatic<CourseValidatorImpl> courseValidator;
    private MockedStatic<DaoProvider> daoProviderHolder;
    @Mock
    private ActivityDaoImpl activityDao;
    @Mock
    private CourseValidator validator;
    @Mock
    private DaoProvider daoProvider;
    @InjectMocks
    private ActivityService activityService = ActivityServiceImpl.getInstance();

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeTest
    public void initStatic() {
        courseValidator = mockStatic(CourseValidatorImpl.class);
        daoProviderHolder = mockStatic(DaoProvider.class);
    }

    @Test(dataProvider = "activityData")
    public void addActivity(Map<String, String> activityData) throws ServiceException, DaoException {
        courseValidator.when(CourseValidatorImpl::getInstance).thenReturn(validator);
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getActivityDao(false)).thenReturn(activityDao);

        when(validator.checkActivity(anyMap())).thenReturn(true);
        when(activityDao.findActivity(any(Activity.class))).thenReturn(false);
        when(activityDao.add(any(Activity.class))).thenReturn(Long.valueOf(1));
        doNothing().when(activityDao).closeConnection();

        boolean actual = activityService.addActivity(activityData);
        Assert.assertTrue(actual);
    }

    @Test
    public void findActivities() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getActivityDao(false)).thenReturn(activityDao);

        when(activityDao.findAll()).thenReturn(List.of(new Activity()));
        doNothing().when(activityDao).closeConnection();

        List<Activity> expected = List.of(new Activity("Инструментальное", "Гитара"));
        List<Activity> actual = activityService.findActivities();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void findCategories() throws ServiceException, DaoException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        when(daoProvider.getActivityDao(false)).thenReturn(activityDao);

        when(activityDao.findCategories()).thenReturn(List.of("Вокальное"));
        doNothing().when(activityDao).closeConnection();

        List<String> expected = List.of("Вокальное", "Хореографическое");
        List<String> actual = activityService.findCategories();
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "activityData")
    public Object[][] getActivityData() {
        return new Object[][]{
                {Map.of(CATEGORY, "Вокальное", TYPE, "Эстрадный вокал")},
                {Map.of(CATEGORY, "Хореографическое", TYPE, "хип-хоп")}
        };
    }
}
