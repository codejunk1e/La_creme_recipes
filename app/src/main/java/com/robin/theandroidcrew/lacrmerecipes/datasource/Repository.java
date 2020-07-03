package com.robin.theandroidcrew.lacrmerecipes.datasource;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.exoplayer2.C;
import com.robin.theandroidcrew.lacrmerecipes.datasource.local.Database;
import com.robin.theandroidcrew.lacrmerecipes.datasource.remote.BakingService;
import com.robin.theandroidcrew.lacrmerecipes.datasource.remote.RecipeEndpoints;
import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.utils.RecipeIdlingResource;


import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    public static final String TAG = Repository.class.getSimpleName();


    public static LiveData<List<Recipe>> getRecipes(RecipeIdlingResource mIdlingResource){
        MutableLiveData<List<Recipe>> data = new MutableLiveData<>();

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

        RecipeEndpoints recipeEndpoints = BakingService.buildService(RecipeEndpoints.class);
         recipeEndpoints.getRecipies().enqueue(
                 new Callback<List<Recipe>>() {
                     @Override
                     public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                         if (response != null){
                             data.postValue(response.body());
                         }
                         else {

                         }
                     }

                     @Override
                     public void onFailure(Call<List<Recipe>> call, Throwable t) {
                         data.postValue(null);
                     }
                 }
         );
         return data;

    }

    public static void addAllFavourites(Context context, List<Ingredient> ingredients){
        int i = 0;
        while (i < ingredients.size()) {
            Database.getInstance(context).ingredientsDao().insertIngredients(ingredients.get(i));
            i++;
        }
    }

    public static void nukeTable(Context context){
        Database.getInstance(context).ingredientsDao().nukeTable();
    }


    public static List<Ingredient>  getAllIngredients(Context context){
           return Database.getInstance(context).ingredientsDao().laoAllIngredients();
        }
}
