package com.codingtive.consumer.task;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.codingtive.consumer.helper.DatabaseContract;
import com.codingtive.consumer.helper.MappingHelper;
import com.codingtive.consumer.interfaces.LoadFavoriteListener;
import com.codingtive.consumer.model.Sport;

import java.lang.ref.WeakReference;
import java.util.List;

public class GetFavoriteTask extends AsyncTask<Void, Void, List<Sport>> {
    private WeakReference<Context> contextReference;
    private LoadFavoriteListener favoriteListener;

    public GetFavoriteTask(Context context, LoadFavoriteListener favoriteListener) {
        contextReference = new WeakReference<>(context);
        this.favoriteListener = favoriteListener;
    }

    @Override
    protected List<Sport> doInBackground(Void... voids) {
        Cursor sportCursor = contextReference.get()
                .getApplicationContext()
                .getContentResolver()
                .query(DatabaseContract.AppDatabase.CONTENT_URI, null, null, null, null);
        return MappingHelper.mapCursorToArrayList(sportCursor);
    }

    @Override
    protected void onPostExecute(List<Sport> sports) {
        super.onPostExecute(sports);
        favoriteListener.onFavoriteLoaded(sports);
    }
}
