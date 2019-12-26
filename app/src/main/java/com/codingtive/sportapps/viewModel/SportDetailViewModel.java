package com.codingtive.sportapps.viewModel;

import android.content.Intent;

import com.codingtive.sportapps.data.model.Sport;
import com.codingtive.sportapps.view.activity.detail.SportDetailActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SportDetailViewModel extends ViewModel {
    private MutableLiveData<Sport> sport = new MutableLiveData<>();

    public void processIntent(Intent intent) {
        if (intent.hasExtra(SportDetailActivity.EXTRA_SPORT)) {
            Sport sportExtra = intent.getParcelableExtra(SportDetailActivity.EXTRA_SPORT);
            sport.postValue(sportExtra);
        }
    }

    public void setIsFavorite(Boolean isFavorite) {
        if (sport.getValue() != null) {
            Sport sportModel = sport.getValue();
            sportModel.setIsFavorite(isFavorite);
            sport.postValue(sportModel);
        }
    }

    public LiveData<Sport> getSport() {
        return sport;
    }
}
