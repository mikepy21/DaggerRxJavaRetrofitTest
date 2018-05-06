package example.com.octocat.ui.home;

import android.support.annotation.VisibleForTesting;

import java.util.List;

import javax.inject.Inject;

import example.com.octocat.data.entities.DataRepository;
import example.com.octocat.data.entities.OctoCat;
import example.com.octocat.di.scopes.PerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@PerView
public class HomePresenter implements HomeContract.Presenter {

    private DataRepository dataRepository;
    @VisibleForTesting
    public
    HomeContract.View view;

    @Inject
    public HomePresenter(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    private void onError(Throwable throwable) {
        view.showError(throwable.toString());
    }

    private void onSuccess(List<OctoCat> repositories) {
        view.showResults(repositories);
    }

    @Override
    public void loadData() {
        final Disposable disposable = dataRepository.getRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
    }


    @Override
    public void attachView(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void detachedView() {
        this.view = null;
    }

    @Override
    public int getActualNData(){
        return view.getActualNData();
    }
}
