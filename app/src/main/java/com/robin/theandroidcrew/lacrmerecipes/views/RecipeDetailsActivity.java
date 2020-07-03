package com.robin.theandroidcrew.lacrmerecipes.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.adapters.IngredientsAdapter;
import com.robin.theandroidcrew.lacrmerecipes.adapters.StepsAdapter;
import com.robin.theandroidcrew.lacrmerecipes.datasource.Repository;
import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.models.Step;
import com.robin.theandroidcrew.lacrmerecipes.utils.AppExecutors;
import com.robin.theandroidcrew.lacrmerecipes.widget.RecipeWidgetProvider;

import java.util.List;

import static com.robin.theandroidcrew.lacrmerecipes.views.MainActivity.POS_KEY;
import static com.robin.theandroidcrew.lacrmerecipes.views.MainActivity.RECIPIE_KEY;

public class RecipeDetailsActivity extends AppCompatActivity implements StepsAdapter.OnItemClick{
    private RecyclerView ingredientRecyclerView;
    private RecyclerView stepRecyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private StepsAdapter stepsAdapter;
    private List<Ingredient> ingredients;
    private int position;
    private List<Step> steps;
    public static final String STEP_KEY = "com.robin.theandroidcrew.STEP_KEY";
    public static final String WIDGET_PREF = "package com.robin.theandroidcrew.lacrmerecipes.WIDGET_PREF" ;
    public static final String WIDGET_PREF_NAME_KEY = "package com.robin.theandroidcrew.lacrmerecipes.WIDGET_PREF_NAME_KEY" ;
    private static final String WIDGET_PREF_POS_KEY = "package com.robin.theandroidcrew.lacrmerecipes.WIDGET_PREF_POS_KEY" ;
    private Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        if (intent.hasExtra(RECIPIE_KEY)) {
            recipe = intent.getParcelableExtra(RECIPIE_KEY);
            position = intent.getIntExtra(POS_KEY, -1);
            ingredients = recipe.getIngredients();
            steps = recipe.getSteps();
            setUpIngredients();
            setUpSteps();
            setActionBarTitle(recipe.getName());

        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null){
            recipe = savedInstanceState.getParcelable(RECIPIE_KEY);
        }

    }

    private void setUpIngredients() {
        ingredientRecyclerView = findViewById(R.id.ingredients_recycler);
        ingredientsAdapter = new IngredientsAdapter(ingredients);
        ingredientRecyclerView.setAdapter(ingredientsAdapter);
    }

    private void setUpSteps() {
        stepRecyclerView = findViewById(R.id.steps_recycler);
        stepsAdapter = new StepsAdapter(steps, this);
        stepRecyclerView.setAdapter(stepsAdapter);
    }
    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void OnClick(int position) {
       naigateView(position);
    }

    public void naigateView(int position){
        if (getResources().getBoolean(R.bool.twoPane)) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(RECIPIE_KEY, recipe);
            arguments.putInt(STEP_KEY, position);
            StepDetailFragment fragment = new StepDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            intent.putExtra(RECIPIE_KEY, recipe);
            intent.putExtra(STEP_KEY, position);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPIE_KEY, recipe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_widget, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_add_widget){
            add();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void add() {

        AppExecutors.getInstance().diskIO().execute(() -> {
            Repository.nukeTable(this.getApplicationContext());
            Repository.addAllFavourites(this.getApplicationContext(), recipe.getIngredients());
            SharedPreferences preferences = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
            preferences.edit().putString(WIDGET_PREF_NAME_KEY, recipe.getName()).apply();

            Intent intent = new Intent(this, RecipeWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] ids = AppWidgetManager.getInstance(getApplication())
                    .getAppWidgetIds(new ComponentName(getApplication(), RecipeWidgetProvider.class));
            AppWidgetManager.getInstance(this).notifyAppWidgetViewDataChanged(ids, R.id.ingredients_list);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            sendBroadcast(intent);
            }
        );



    }
}