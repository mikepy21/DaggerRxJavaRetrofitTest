package example.com.octocat;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import example.com.octocat.data.GitHubService;
import example.com.octocat.data.entities.DataRepository;
import example.com.octocat.data.entities.OctoCat;
import example.com.octocat.ui.home.HomeContract;
import example.com.octocat.ui.home.HomePresenter;
import example.com.octocat.util.SchedulerProvider;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MainPresenterTest {

    @Mock
    private HomePresenter homePresenter;

    @Mock
    GitHubService gitHubService;

    @InjectMocks
    DataRepository dataRepository;

    @Mock
    HomeContract homeContract;


//    @Mock
//    SchedulerProvider schedulerProvider;

    @Mock
    HomeContract.View view;


    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }


    @Before
    public void setUp(){
        homePresenter = new HomePresenter(dataRepository);
        homePresenter.attachView(view);

        List<OctoCat> repositories = new ArrayList<>();
        OctoCat repository1 = new OctoCat(1,"Test1");
        OctoCat repository2 = new OctoCat(2,"Test2");
        OctoCat repository3 = new OctoCat(3,"Test3");
        OctoCat repository4 = new OctoCat(4,"Test4");
        OctoCat repository5 = new OctoCat(5,"Test5");
        repositories.add(repository1);
        repositories.add(repository2);
        repositories.add(repository3);
        repositories.add(repository4);
        repositories.add(repository5);

        when(gitHubService.retrieveRepos()).thenReturn(Single.just(repositories));


    }

    @After
    public void tearDown(){
        homePresenter.detachedView();
        homePresenter=null;
    }


    @Test
    public void testOnAttachView_viewShouldBeNotNull(){
        homePresenter.attachView(view);

        assertNotNull("The view should be inflated", homePresenter.view);
    }

    @Test
    public void testOnDetachView_viewShouldBeNull(){
        homePresenter.attachView(view);
        homePresenter.detachedView();

        assertNull("The view should be null ", homePresenter.view);
    }

    @Test
    public void testData(){
        homePresenter.loadData();

        assertEquals(1,homePresenter.getActualNData()>0?0:1);

    }

}
