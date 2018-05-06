package example.com.octocat.ui.home;

import java.util.ArrayList;
import java.util.List;

import example.com.octocat.data.entities.OctoCat;

public interface HomeContract {
    interface View {
        void showResults(List<OctoCat> octoCatList);

        void showError(String error);

        int getActualNData();
    }

    interface Presenter{
        void loadData();
        void attachView (View view);
        void detachedView();
        int getActualNData();

    }
}
