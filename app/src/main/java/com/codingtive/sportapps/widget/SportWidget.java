package com.codingtive.sportapps.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.codingtive.sportapps.R;
import com.codingtive.sportapps.data.model.Sport;
import com.codingtive.sportapps.view.activity.detail.SportDetailActivity;

public class SportWidget extends AppWidgetProvider {

    static final String EXTRA_SPORT = "extra_sport";
    static final String KEY_WIDGET_ACTION = "key_widget_action";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, WidgetRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sport_widget);
        views.setRemoteAdapter(R.id.stack_view, intent);
        views.setEmptyView(R.id.stack_view, R.id.empty_view);

        Intent widgetIntent = new Intent(context, SportWidget.class);
        widgetIntent.setAction(KEY_WIDGET_ACTION);
        widgetIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100,
                widgetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_view, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null && intent.getAction().equals(KEY_WIDGET_ACTION)) {
            Sport sport = intent.getParcelableExtra(EXTRA_SPORT);
            openSportDetail(context, sport);
        } else {
            Log.d("test", "test");
        }
        updateWidgetComponent(context);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }

    private void openSportDetail(Context context, Sport sport) {
        Intent intent = new Intent(context, SportDetailActivity.class);
        intent.putExtra(SportDetailActivity.EXTRA_SPORT, sport);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void updateWidgetComponent(Context context) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, SportWidget.class);
        int[] widgetIds = widgetManager.getAppWidgetIds(componentName);
        if (widgetIds != null) {
            widgetManager.notifyAppWidgetViewDataChanged(widgetIds, R.id.stack_view);
        }
    }
}

