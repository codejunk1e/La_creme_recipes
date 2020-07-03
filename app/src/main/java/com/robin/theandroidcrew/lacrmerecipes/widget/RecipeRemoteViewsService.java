package com.robin.theandroidcrew.lacrmerecipes.widget;

import android.content.Intent;

public class RecipeRemoteViewsService extends android.widget.RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoveViewFactory(this.getApplication(), intent);
    }
}
