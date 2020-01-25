package com.codingtive.consumer.interfaces;

import com.codingtive.consumer.model.Sport;

import java.util.List;

interface LoadDataListener {
    void onDataLoaded(List<Sport> sports);
}
