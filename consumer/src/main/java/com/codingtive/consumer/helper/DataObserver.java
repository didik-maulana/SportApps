package com.codingtive.consumer.helper;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.codingtive.consumer.interfaces.LoadFavoriteListener;
import com.codingtive.consumer.task.GetFavoriteTask;

import java.lang.ref.WeakReference;

public class DataObserver {
    public static class Observer extends ContentObserver {
        private WeakReference<Context> contextReference;

        public Observer(Handler handler, Context context) {
            super(handler);
            contextReference = new WeakReference<>(context);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new GetFavoriteTask(contextReference.get(), (LoadFavoriteListener) contextReference.get()).execute();
        }
    }
}
