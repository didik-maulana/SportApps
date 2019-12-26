package com.codingtive.sportapps.viewModel;

import com.codingtive.sportapps.data.model.Sport;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<Sport>> sports = new MutableLiveData<>();

    public LiveData<List<Sport>> getSportList() {
        return sports;
    }

    public void setSportList(List<Sport> sports) {
        this.sports.postValue(sports);
    }
}
