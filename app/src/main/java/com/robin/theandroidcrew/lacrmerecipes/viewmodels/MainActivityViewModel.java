package com.robin.theandroidcrew.lacrmerecipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.robin.theandroidcrew.lacrmerecipes.datasource.Repository;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.utils.RecipeIdlingResource;

import java.util.List;

/*
*   Shared ViewModel for Fragments
* */
public class MainActivityViewModel extends ViewModel {

    public LiveData<List<Recipe>> getRecipes(RecipeIdlingResource mIdlingResource){
        return Repository.getRecipes(mIdlingResource);
    }
}
