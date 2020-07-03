package com.robin.theandroidcrew.lacrmerecipes.utils;

import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

        return false;
    }

/*
    dummyRecipe =  new Recipe();
        dummyRecipe.setId(1);
        dummyRecipe.setName("dummy recipe");
    Ingredient ingredient = new Ingredient();
        ingredient.setIngredient("Spice");
        ingredient.setMeasure("12");
        ingredient.setQuantity(12F);
    List<Ingredient> list = new ArrayList<>();
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        dummyRecipe.setIngredients(list);
        intent.putExtra(RECIPIE_KEY, dummyRecipe);*/
}
