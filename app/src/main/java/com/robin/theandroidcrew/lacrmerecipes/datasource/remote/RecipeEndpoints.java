package com.robin.theandroidcrew.lacrmerecipes.datasource.remote;

import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeEndpoints {

    @GET("baking.json")
    public Call<List<Recipe>> getRecipies();
}
