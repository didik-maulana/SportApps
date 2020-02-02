package com.codingtive.sportapps.view.fragment.favorite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingtive.sportapps.R;
import com.codingtive.sportapps.adapter.SportAdapter;
import com.codingtive.sportapps.data.database.RoomClient;
import com.codingtive.sportapps.data.model.Sport;
import com.codingtive.sportapps.interfaces.ItemSportListener;
import com.codingtive.sportapps.view.activity.detail.SportDetailActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class FavoriteFragment extends Fragment implements ItemSportListener {

    private TextView emptyTextView;
    private RecyclerView sportsRecyclerView;
    private SportAdapter sportAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FavoriteViewModel favoriteViewModel;
    private GetFavoriteTask getFavoriteTask;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        bindView(view);
        setupSportsRecyclerView();
        registerObserver();
        setupRefreshLayout();
        getFavoriteTask.execute();
    }

    private void initialize() {
        initViewModel();
        initFavoriteTask();
    }

    private void initFavoriteTask() {
        getFavoriteTask = new GetFavoriteTask(this);
    }

    private void initViewModel() {
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
    }

    private void bindView(View view) {
        emptyTextView = view.findViewById(R.id.tv_empty);
        sportsRecyclerView = view.findViewById(R.id.rv_sports);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    }

    private void setupSportsRecyclerView() {
        sportAdapter = new SportAdapter(this);
        sportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sportsRecyclerView.setAdapter(sportAdapter);
    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFavorite();
            }
        });
    }

    private void refreshFavorite() {
        getFavoriteTask = null;
        initFavoriteTask();
        getFavoriteTask.execute();
    }

    private void registerObserver() {
        favoriteViewModel.getSportList().observe(this, new Observer<List<Sport>>() {
            @Override
            public void onChanged(List<Sport> sports) {
                emptyTextView.setVisibility(sports.isEmpty() ? View.VISIBLE : View.GONE);
                sportAdapter.setSportList(sports);
                sportAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClicked(Sport sport) {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), SportDetailActivity.class);
            intent.putExtra(SportDetailActivity.EXTRA_SPORT, sport);
            getActivity().startActivity(intent);
        }
    }

    public static class GetFavoriteTask extends AsyncTask<Void, Void, List<Sport>> {
        private WeakReference<FavoriteFragment> fragment;

        GetFavoriteTask(FavoriteFragment fragment) {
            this.fragment = new WeakReference<>(fragment);
        }

        @Override
        protected List<Sport> doInBackground(Void... voids) {
            return RoomClient.getInstance(fragment.get().getContext())
                    .getSportDatabase()
                    .getSportDao()
                    .getSportList();
        }

        @Override
        protected void onPostExecute(List<Sport> sports) {
            super.onPostExecute(sports);
            fragment.get().favoriteViewModel.setSportList(sports);
            fragment.get().swipeRefreshLayout.setRefreshing(false);
        }
    }
}
