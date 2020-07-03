package com.robin.theandroidcrew.lacrmerecipes.datasource.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;

import java.util.List;

@Dao
public interface IngredientsDao {

    @Query("SELECT * FROM favourite_recipe_ingredients_table ORDER BY id")
    List<Ingredient> laoAllIngredients();

    @Insert()
    void insertIngredients(Ingredient ingredient);

    @Query("DELETE FROM favourite_recipe_ingredients_table")
     void nukeTable();
}
