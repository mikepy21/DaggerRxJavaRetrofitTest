package example.com.octocat.ui.home;

import dagger.Component;
import example.com.octocat.di.MainComponent;
import example.com.octocat.di.scopes.PerView;

@PerView
@Component(dependencies = MainComponent.class)
public interface HomeComponent   {
    void inject(HomeActivity homeActivity);
}
