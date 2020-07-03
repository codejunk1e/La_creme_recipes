package com.robin.theandroidcrew.lacrmerecipes.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.views.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.robin.theandroidcrew.lacrmerecipes.views.MainActivity.RECIPIE_KEY;
import static com.robin.theandroidcrew.lacrmerecipes.views.RecipeDetailsActivity.WIDGET_PREF;
import static com.robin.theandroidcrew.lacrmerecipes.views.RecipeDetailsActivity.WIDGET_PREF_NAME_KEY;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        Intent intent = new Intent(context, RecipeRemoteViewsService.class);


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setRemoteAdapter(R.id.ingredients_list, intent);

        SharedPreferences preferences = context.getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        views.setTextViewText(R.id.recipeTitle, preferences.getString(WIDGET_PREF_NAME_KEY, ""));

        Intent appIntent = new Intent(context, MainActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);
        views.setOnClickPendingIntent(R.id.parent_view, appPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}