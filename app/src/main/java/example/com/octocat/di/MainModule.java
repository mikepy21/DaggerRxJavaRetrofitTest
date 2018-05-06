package example.com.octocat.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import example.com.octocat.data.GitHubService;

@Module
public class MainModule {
    @Singleton
    @Provides
    public GitHubService provideGitHubService(){
        return new GitHubService.Factory().create();
    }
}
