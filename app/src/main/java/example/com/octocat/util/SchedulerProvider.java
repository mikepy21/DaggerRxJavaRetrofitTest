package example.com.octocat.util;


import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class SchedulerProvider {

    @Inject
    SchedulerProvider(){

    }

    public Scheduler io(){
        return Schedulers.io();
    }

    public Scheduler ui(){
        return AndroidSchedulers.mainThread();
    }
}