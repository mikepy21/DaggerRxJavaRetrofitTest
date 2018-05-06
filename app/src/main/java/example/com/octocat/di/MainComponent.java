package example.com.octocat.di;

import javax.inject.Singleton;

import dagger.Component;
import example.com.octocat.data.entities.DataRepository;

@Singleton
@Component(modules= MainModule.class)
public interface MainComponent {
    DataRepository dataRepository();
}
