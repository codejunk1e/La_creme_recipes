package com.robin.theandroidcrew.lacrmerecipes.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.models.Step;

import static com.robin.theandroidcrew.lacrmerecipes.views.MainActivity.RECIPIE_KEY;
import static com.robin.theandroidcrew.lacrmerecipes.views.RecipeDetailsActivity.STEP_KEY;

public class StepDetailActivity extends AppCompatActivity {
    private Recipe recipe;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        frameLayout = findViewById(R.id.item_detail_container);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            loadFragement(getIntent().getIntExtra(STEP_KEY, -1));
        }
    }

    public void loadFragement(int position){
        Bundle arguments = new Bundle();
        recipe = getIntent().getParcelableExtra(RECIPIE_KEY);
        arguments.putParcelable(RECIPIE_KEY, recipe);
        arguments.putInt(STEP_KEY, position);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit();
    }

    public void setImmersive(){
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE);
    }
    public void removeImmersive(){
        if(getSupportActionBar()!=null) {
            getSupportActionBar().show();
        }

        getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

    }

    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}