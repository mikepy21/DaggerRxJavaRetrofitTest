package example.com.octocat.data.entities;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import example.com.octocat.data.GitHubService;
import io.reactivex.Single;

@Singleton
public class DataRepository {

    private GitHubService gitHubService;

    @Inject
    public DataRepository(GitHubService gitHubService){
        this.gitHubService = gitHubService;
    }

    public Single<List<OctoCat>> getRepositories(){
        return gitHubService.retrieveRepos();
    }
}
