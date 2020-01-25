package com.codingtive.consumer.helper;

import android.database.ContentObserver;
import android.os.Handler;

import com.codingtive.consumer.view.MainActivity;

import java.lang.ref.WeakReference;

public class DataObserver {
    public static class Observer extends ContentObserver {
        private WeakReference<MainActivity> activityReference;

        public Observer(Handler handler, MainActivity activity) {
            super(handler);
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            activityReference.get().getFavoriteSports();
        }
    }
}
