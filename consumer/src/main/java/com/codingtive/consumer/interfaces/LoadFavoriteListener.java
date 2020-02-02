package com.codingtive.consumer.interfaces;

import com.codingtive.consumer.model.Sport;

import java.util.List;

public interface LoadFavoriteListener {
    void onFavoriteLoaded(List<Sport> sports);
}
