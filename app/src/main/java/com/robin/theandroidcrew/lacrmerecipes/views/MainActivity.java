package com.robin.theandroidcrew.lacrmerecipes.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.adapters.RecipieAdapter;
import com.robin.theandroidcrew.lacrmerecipes.datasource.Repository;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.utils.NetworkUtils;
import com.robin.theandroidcrew.lacrmerecipes.utils.RecipeIdlingResource;
import com.robin.theandroidcrew.lacrmerecipes.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipieAdapter.OnItemClick {
    @Nullable
    private RecipeIdlingResource mIdlingResource;
    public static final String RECIPIE_KEY = "com.robin.theandroidcrew.RECIPE_KEY";
    public static final String POS_KEY = "com.robin.theandroidcrew.POS_KEY";
    private RecyclerView recyclerView;
    private List<Recipe> theRecipeList = new ArrayList<>();
    private MainActivityViewModel mViewModel;
    public static final String TAG = MainActivity.class.getSimpleName();
    private RecipieAdapter recipieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recipeRecycler);
        recyclerView.setHasFixedSize(true);
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        if (NetworkUtils.isOnline()) {
            getRecipes(mIdlingResource);

        } else {
            Toast.makeText(this, "Failed to retrieve Recipes", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getRecipes(RecipeIdlingResource mIdlingResource) {
        mViewModel.getRecipes(mIdlingResource).observe(
                this, recipes -> {
                    if (recipes == null){
                        Log.d(TAG, "Value is null");
                    }
                    else {
                        theRecipeList = recipes;
                        recipieAdapter = new RecipieAdapter(theRecipeList, this);
                        recyclerView.setAdapter(recipieAdapter);

                    }
                }
        );


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
            Log.d(TAG, "Reasing Idling resource");
        }

    }

    @Override
    public void OnClick(int position) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(RECIPIE_KEY, theRecipeList.get(position));
        intent.putExtra(POS_KEY, position);
        startActivity(intent);

    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipeIdlingResource();
        }
        return mIdlingResource;
    }
}