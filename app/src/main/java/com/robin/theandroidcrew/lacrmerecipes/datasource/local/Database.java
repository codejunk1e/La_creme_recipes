package com.robin.theandroidcrew.lacrmerecipes.datasource.local;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;

@androidx.room.Database(entities = {Ingredient.class}, exportSchema = false, version = 1)
public abstract class Database extends RoomDatabase {
    private static final String TAG = Database.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "IngredientsDatabase";
    private static Database sInstance;

    public static Database getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        Database.class, Database.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract IngredientsDao ingredientsDao();
}
