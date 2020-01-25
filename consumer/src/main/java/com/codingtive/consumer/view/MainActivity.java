package com.codingtive.consumer.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.TextView;

import com.codingtive.consumer.R;
import com.codingtive.consumer.adapter.SportAdapter;
import com.codingtive.consumer.helper.DataObserver;
import com.codingtive.consumer.helper.DatabaseContract;
import com.codingtive.consumer.helper.MappingHelper;
import com.codingtive.consumer.model.Sport;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

                if (sports.isEmpty()) {
                    emptyTextView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void getFavoriteSports() {
        new GetFavoriteTask(this).execute();
    }

    public static class GetFavoriteTask extends AsyncTask<Void, Void, List<Sport>> {
        private final WeakReference<MainActivity> activityReference;

        GetFavoriteTask(MainActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        protected List<Sport> doInBackground(Void... voids) {
            Cursor sportCursor = activityReference.get()
                    .getApplicationContext()
                    .getContentResolver()
                    .query(DatabaseContract.AppDatabase.CONTENT_URI, null, null, null, null);
            return MappingHelper.mapCursorToArrayList(sportCursor);
        }

        @Override
        protected void onPostExecute(List<Sport> sports) {
            super.onPostExecute(sports);
            activityReference.get().mainViewModel.setSports(sports);
        }
    }
}
