package example.com.octocat.data;

import java.util.List;

import example.com.octocat.data.entities.OctoCat;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface GitHubService {

    public static final String BASE_URL = "https://api.github.com/";

    @GET("/users/octocat/repos")
    Single<List<OctoCat>> retrieveRepos();

    class Factory{
        public GitHubService create(){
            return new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(GitHubService.class);
        }
    }
}
