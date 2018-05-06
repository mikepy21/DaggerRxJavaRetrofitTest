package example.com.octocat;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.com.octocat.di.DaggerMainComponent;
import example.com.octocat.di.MainComponent;
import example.com.octocat.di.MainModule;

public class MyApp extends Application {

    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mainComponent = DaggerMainComponent
                            .builder()
                            .mainModule(new MainModule())
                            .build();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }
}
