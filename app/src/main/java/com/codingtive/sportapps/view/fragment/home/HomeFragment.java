package com.codingtive.sportapps.view.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codingtive.sportapps.R;
import com.codingtive.sportapps.adapter.SportAdapter;
import com.codingtive.sportapps.data.model.Sport;
import com.codingtive.sportapps.interfaces.ItemSportListener;
import com.codingtive.sportapps.view.activity.detail.SportDetailActivity;
import com.codingtive.sportapps.viewModel.HomeViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment implements ItemSportListener {
    private RecyclerView sportsRecyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SportAdapter sportAdapter;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        initViewModel();
        registerObserver();
        setupSportsRecyclerView();
        setupSwipeRefresh();
        homeViewModel.getSports();
    }

    private void bindView(View view) {
        sportsRecyclerView = view.findViewById(R.id.rv_sports);
        progressBar = view.findViewById(R.id.progress_loading);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
    }

    private void initViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    private void setupSportsRecyclerView() {
        sportAdapter = new SportAdapter(this);
        sportsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        sportsRecyclerView.setAdapter(sportAdapter);
    }

    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeViewModel.getSports();
                swipeRefreshLayout.setRefreshing(false);
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

    private void registerObserver() {
        observeIsLoading();
        observeMessage();
        observeSportList();
    }

    private void observeIsLoading() {
        homeViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                handleLoading(isLoading);
            }
        });
    }

    private void observeMessage() {
        homeViewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void observeSportList() {
        homeViewModel.getSportList().observe(this, new Observer<List<Sport>>() {
            @Override
            public void onChanged(List<Sport> sports) {
                sportAdapter.setSportList(sports);
                sportAdapter.notifyDataSetChanged();
            }
        });
    }

    private void handleLoading(Boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}
