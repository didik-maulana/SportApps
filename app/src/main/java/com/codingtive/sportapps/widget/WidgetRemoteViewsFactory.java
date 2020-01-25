package com.codingtive.sportapps.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.codingtive.sportapps.R;
import com.codingtive.sportapps.data.database.RoomClient;
import com.codingtive.sportapps.data.model.Sport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private List<Sport> sports = new ArrayList<>();

    WidgetRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        sports = RoomClient.getInstance(context)
                .getSportDatabase()
                .getSportDao()
                .getSportList();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return sports.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_sport_widget);

        if (!sports.isEmpty()) {
            Sport sport = sports.get(position);
            try {
                Bitmap imageBitmap = Glide.with(context)
                        .asBitmap()
                        .load(sport.getStrSportThumb())
                        .submit()
                        .get();
                remoteViews.setImageViewBitmap(R.id.img_sport_widget, imageBitmap);
                remoteViews.setTextViewText(R.id.tv_sport_title_widget, sport.getStrSport());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent();
            intent.putExtra(SportWidget.EXTRA_SPORT, sport);
            remoteViews.setOnClickFillInIntent(R.id.container_view_widget, intent);
        }

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
