package example.com.octocat.ui.home;

import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.octocat.MyApp;
import example.com.octocat.R;
import example.com.octocat.data.entities.OctoCat;
import example.com.octocat.di.MainComponent;

public class HomeActivity extends AppCompatActivity implements HomeContract.View{

    @Inject
    HomePresenter homePresenter;

    @VisibleForTesting
    public List<OctoCat> actualList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        injectDependencies();

        homePresenter.attachView(this);
        homePresenter.loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homePresenter.detachedView();
    }

    @Override
    public void showResults(List<OctoCat> octoCatList) {
        this.actualList.clear();
        this.actualList.addAll(octoCatList);
        Log.d("TAG", "showResults: "+octoCatList);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getActualNData() {
        return actualList.size();
    }

    private void injectDependencies(){
        MainComponent mainComponent = ((MyApp) getApplication()).getMainComponent();

        DaggerHomeComponent.builder()
                .mainComponent(mainComponent)
                .build()
                .inject(this);
    }

}
