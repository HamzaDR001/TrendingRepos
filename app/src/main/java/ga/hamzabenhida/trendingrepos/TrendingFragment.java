package ga.hamzabenhida.trendingrepos;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ga.hamzabenhida.trendingrepos.helpers.*;


/**
 * Trending Fragment.
 */
public class TrendingFragment extends Fragment {

    // list of repository loaded from the API
    private ArrayList<RepoItem> repoItems;
    // RecyclerView Adapter
    private MListAdapter listAdapter;
    // page reached by the user (pagination)
    private int page = 1;
    // current date string
    private String date = "";

    // would be true if the application is loading data in the background
    // prevents the application from sending multiple requests to
    // the API at the same time.
    public static boolean isLoading = false;


    public TrendingFragment() {
        repoItems = new ArrayList<RepoItem>();
    }

    public ArrayList<RepoItem> getRepoItems() {
        return repoItems;
    }

    public void setRepoItems(ArrayList<RepoItem> repoItems) {
        this.repoItems = repoItems;
    }

    public void addRepoItem(RepoItem ri){
        repoItems.add(ri);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inf = inflater.inflate(R.layout.fragment_trending, container, false);
        //initiating the List Adapter
        listAdapter = new MListAdapter(this);
        // computing date.now - 1 month and casting to string
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date result = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date = format.format(result);

        // running the first request to get data from the API
        new JsonTask(getRepoItems(), listAdapter).execute("https://api.github.com/search/repositories?q=created:>"+date+"&sort=stars&order=desc&page="+(page++));

        return inf;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initiating the RecyclerView and setting the adapter
        final RecyclerView listView = getView().findViewById(R.id.list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listView.setLayoutManager(linearLayoutManager);
        listView.getItemAnimator().setChangeDuration(0);
        listView.setHasFixedSize(true);
        listView.setAdapter(listAdapter);

        // setting a scroll listener to load more data when user can't scroll down anymore, and notifying the RecyclerView to refresh itself
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    if(isLoading) return;
                    repoItems.get(repoItems.size()-1).setDesc("Loading ...");
                    listAdapter.notifyDataSetChanged();
                    new JsonTask(getRepoItems(), listAdapter).execute("https://api.github.com/search/repositories?q=created:>"+date+"&sort=stars&order=desc&page="+(page++));
                    listAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }
        });

        listAdapter.notifyDataSetChanged();
    }
}
