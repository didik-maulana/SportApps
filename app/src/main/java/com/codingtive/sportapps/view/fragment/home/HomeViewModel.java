package com.codingtive.sportapps.view.fragment.home;

import com.codingtive.sportapps.data.api.RetrofitClient;
import com.codingtive.sportapps.data.model.Sport;
import com.codingtive.sportapps.data.response.SportResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Sport>> sports = new MutableLiveData<>();
    private MutableLiveData<String> message = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public void getSports() {
        isLoading.postValue(true);
        new RetrofitClient().getClient().getSports().enqueue(getSportsCallback());
    }

    private Callback<SportResponse> getSportsCallback() {
        return new Callback<SportResponse>() {
            @Override
            public void onResponse(Call<SportResponse> call,
                                   Response<SportResponse> response) {
                isLoading.postValue(false);
                if (response.body() != null) {
                    sports.postValue(response.body().getSports());
                } else {
                    message.postValue("Sports not available");
                }
            }

            @Override
            public void onFailure(Call<SportResponse> call, Throwable t) {
                isLoading.postValue(false);
                message.postValue("Failed load sport data");
            }
        };
    }

    public LiveData<List<Sport>> getSportList() {
        return sports;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getMessage() {
        return message;
    }
}
