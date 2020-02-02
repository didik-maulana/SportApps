package com.codingtive.consumer.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.TextView;

import com.codingtive.consumer.R;
import com.codingtive.consumer.adapter.SportAdapter;
import com.codingtive.consumer.helper.DataObserver;
import com.codingtive.consumer.helper.DatabaseContract;
import com.codingtive.consumer.interfaces.LoadFavoriteListener;
import com.codingtive.consumer.model.Sport;
import com.codingtive.consumer.task.GetFavoriteTask;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity implements LoadFavoriteListener {
    private RecyclerView sportRecyclerView;
    private TextView emptyTextView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private SportAdapter sportAdapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewModel();
        bindView();
        setupRecyclerView();
        setupRefreshLayout();
        observeSportList();
        registerContentObserver();
        getFavoriteSports();
    }

    @Override
    public void onFavoriteLoaded(List<Sport> sports) {
        mainViewModel.setSports(sports);
    }

    private void initViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    private void bindView() {
        sportRecyclerView = findViewById(R.id.rv_sports);
        emptyTextView = findViewById(R.id.tv_empty_sport);
        swipeRefreshLayout = findViewById(R.id.refresh_view);
    }

    private void setupRecyclerView() {
        sportAdapter = new SportAdapter();
        sportRecyclerView.setAdapter(sportAdapter);
    }

    private void setupRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFavoriteSports();
            }
        });
    }

    private void registerContentObserver() {
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());

        getContentResolver().registerContentObserver(
                DatabaseContract.AppDatabase.CONTENT_URI,
                true,
                new DataObserver.Observer(handler, this));
    }

    private void observeSportList() {
        mainViewModel.getSports().observe(this, new Observer<List<Sport>>() {
            @Override
            public void onChanged(List<Sport> sports) {
                sportAdapter.setSportList(sports);
                swipeRefreshLayout.setRefreshing(false);

                if (sportAdapter.getItemCount() == 0) {
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void getFavoriteSports() {
        new GetFavoriteTask(this, this).execute();
    }
}
