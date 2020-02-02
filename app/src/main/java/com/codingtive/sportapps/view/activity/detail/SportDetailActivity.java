package com.codingtive.sportapps.view.activity.detail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codingtive.sportapps.R;
import com.codingtive.sportapps.data.database.RoomClient;
import com.codingtive.sportapps.data.model.Sport;
import com.codingtive.sportapps.widget.SportWidget;

import java.lang.ref.WeakReference;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class SportDetailActivity extends AppCompatActivity {

    public static final String EXTRA_SPORT = "extra_sport";
    private ImageView sportImageView;
    private TextView titleTextView, descriptionTextView;
    private Button favoriteButton;
    private SportDetailViewModel detailViewModel;
    private InsertFavoriteTask insertFavoriteTask;
    private RemoveFavoriteTask removeFavoriteTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_detail);
        bindView();
        setupToolbar();
        setupViewModel();
        registerObserver();
        detailViewModel.processIntent(getIntent());
    }

    private void bindView() {
        sportImageView = findViewById(R.id.img_sport);
        titleTextView = findViewById(R.id.tv_sport_title);
        descriptionTextView = findViewById(R.id.tv_sport_description);
        favoriteButton = findViewById(R.id.btn_favorite);
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_sport_detail));
            getSupportActionBar().setElevation(0f);
        }
    }

    private void setupViewModel() {
        detailViewModel = ViewModelProviders.of(this).get(SportDetailViewModel.class);
    }

    private void registerObserver() {
        detailViewModel.getSport().observe(this, new Observer<Sport>() {
            @Override
            public void onChanged(Sport sport) {
                initFavoriteTask();
                checkFavoriteSport(sport);
                setDetailView(sport);
                setFavoriteButton(sport.getIsFavorite());
            }
        });
    }

    private void setDetailView(Sport sport) {
        Glide.with(this)
                .load(sport.getStrSportThumb())
                .centerCrop()
                .into(sportImageView);

        titleTextView.setText(sport.getStrSport());
        descriptionTextView.setText(sport.getStrSportDescription());

        favoriteButton.setOnClickListener(getFavoriteListener());
    }

    private View.OnClickListener getFavoriteListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isFavorite = Objects.requireNonNull(detailViewModel.getSport().getValue()).getIsFavorite();
                if (isFavorite != null) {
                    handleFavoriteClicked(isFavorite);
                }
            }
        };
    }

    private void handleFavoriteClicked(Boolean isFavorite) {
        detailViewModel.setIsFavorite(isFavorite);
        if (isFavorite) {
            removeFavoriteTask.execute();
        } else {
            insertFavoriteTask.execute();
        }
    }

    private void checkFavoriteSport(Sport sport) {
        new GetFavoriteTask(this, sport.getIdSport()).execute();
    }

    private void setFavoriteButton(Boolean isFavorite) {
        favoriteButton.setText(getString(isFavorite ? R.string.action_remove_favorite : R.string.action_add_to_favorite));
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initFavoriteTask() {
        String sportId = Objects.requireNonNull(detailViewModel.getSport().getValue()).getIdSport();
        removeFavoriteTask = new RemoveFavoriteTask(this, sportId);
        insertFavoriteTask = new InsertFavoriteTask(this);
    }

    private void updateWidget() {
        new SportWidget().updateWidgetComponent(this);
    }

    private static class GetFavoriteTask extends AsyncTask<Void, Void, Sport> {
        private String sportId;
        private WeakReference<SportDetailActivity> activity;

        GetFavoriteTask(SportDetailActivity activity, String sportId) {
            this.activity = new WeakReference<>(activity);
            this.sportId = sportId;
        }

        @Override
        protected Sport doInBackground(Void... voids) {
            return RoomClient.getInstance(activity.get())
                    .getSportDatabase()
                    .getSportDao()
                    .getSport(sportId);
        }

        @Override
        protected void onPostExecute(Sport sport) {
            super.onPostExecute(sport);
            if (sport != null) {
                activity.get().detailViewModel.setIsFavorite(sport.getIsFavorite());
            } else {
                activity.get().detailViewModel.setIsFavorite(false);
            }
        }
    }

    private static class InsertFavoriteTask extends AsyncTask<Void, Void, Void> {
        private WeakReference<SportDetailActivity> activity;

        InsertFavoriteTask(SportDetailActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Sport sport = activity.get().detailViewModel.getSport().getValue();
            if (sport != null) {
                sport.setIsFavorite(true);
                RoomClient.getInstance(activity.get())
                        .getSportDatabase()
                        .getSportDao()
                        .addFavorite(sport);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.get().showMessage(activity.get().getString(R.string.msg_success_add_favorite));
            new SportWidget().updateWidgetComponent(activity.get().getApplicationContext());
        }
    }

    private static class RemoveFavoriteTask extends AsyncTask<Void, Void, Void> {
        private String id;
        private WeakReference<SportDetailActivity> activity;

        RemoveFavoriteTask(SportDetailActivity activity, String id) {
            this.activity = new WeakReference<>(activity);
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            RoomClient.getInstance(activity.get())
                    .getSportDatabase()
                    .getSportDao()
                    .removeFavorite(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.get().showMessage(activity.get().getString(R.string.msg_removed_from_favorite));
            activity.get().updateWidget();
        }
    }
}
