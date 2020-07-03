package com.robin.theandroidcrew.lacrmerecipes.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.datasource.Repository;
import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.utils.AppExecutors;

import java.util.List;

import static com.robin.theandroidcrew.lacrmerecipes.views.MainActivity.RECIPIE_KEY;


public class RecipeRemoveViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    List<Ingredient> ingredientList;


    public RecipeRemoveViewFactory(Context context, Intent intent) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            ingredientList = Repository.getAllIngredients(context);
            }
        );
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredientList != null){
            return ingredientList.size();
        }
        else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredientList == null || ingredientList.size() == 0) return null;
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_item_list);
        remoteView.setTextViewText(R.id.recipie_text, ingredientList.get(position).getIngredient());
        return remoteView;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
