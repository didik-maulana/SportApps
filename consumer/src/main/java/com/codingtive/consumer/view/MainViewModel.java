package com.codingtive.consumer.view;

import com.codingtive.consumer.model.Sport;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Sport>> sports = new MutableLiveData<>();

    public void setSports(List<Sport> sports) {
        this.sports.postValue(sports);
    }

    LiveData<List<Sport>> getSports() {
        return sports;
    }
}
