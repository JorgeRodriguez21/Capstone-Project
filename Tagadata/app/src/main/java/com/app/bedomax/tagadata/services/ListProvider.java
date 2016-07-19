package com.app.bedomax.tagadata.services;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.activities.DescriptionActivity;
import com.app.bedomax.tagadata.models.New;

import java.util.ArrayList;

/**
 * If you are familiar with Adapter of ListView,this is the same as adapter
 * with few changes
 *
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    public static ArrayList <New> newsList = new ArrayList<>();
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    /*
*Similar to getView of Adapter where instead of View
*we return RemoteViews
*
*/
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_row);
        New listItem = newsList.get(position);
        remoteView.setTextViewText(R.id.name, listItem.getName());
        Intent next = new Intent(context, DescriptionActivity.class);
        next.putExtra(context.getString(R.string.urlWord), listItem.getUrl());
        next.putExtra(context.getString(R.string.titleWord), listItem.getName());
        next.putExtra(context.getString(R.string.newWord), listItem);
        remoteView.setOnClickFillInIntent(R.id.widgetRL,next);
        remoteView.setOnClickFillInIntent(R.id.name,next);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(new ComponentName(context, ListProvider.class), remoteView);
        return remoteView;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
