package com.robin.theandroidcrew.lacrmerecipes.views;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.models.Step;

import static com.robin.theandroidcrew.lacrmerecipes.views.MainActivity.RECIPIE_KEY;
import static com.robin.theandroidcrew.lacrmerecipes.views.RecipeDetailsActivity.STEP_KEY;

public class StepDetailFragment extends Fragment implements Player.EventListener, View.OnClickListener{
    private static final String PLAYER_POSITON_KEY = "com.robin.theandroidcrew.lacrmerecipes.PLAYER_POSITON_KEY";
    int position;
    private Recipe recipe;
    Step step;
    PlayerView exoPlayer;
    TextView stepDescription;
    TextView videoNotAvailable;
    TextView stepDescriptionDetails;
    FloatingActionButton fabNext;
    FloatingActionButton fabPrevious;
    private String videoUrl;
    private long lastPos;
    private SimpleExoPlayer mExoPlayer;


    public StepDetailFragment() {
    }

    public static StepDetailFragment newInstance() {
        return new StepDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(STEP_KEY);
            recipe = getArguments().getParcelable(RECIPIE_KEY);
            step = recipe.getSteps().get(position);
            if (!step.getVideoURL().equals("")){
                videoUrl = step.getVideoURL();
            }
            else {
                videoUrl = null;
            }

            if (getResources().getBoolean(R.bool.twoPane)){

                ((RecipeDetailsActivity) getActivity())
                        .setActionBarTitle(step.getShortDescription());
            }
            else {

                ((StepDetailActivity) getActivity())
                        .setActionBarTitle(step.getShortDescription());
            }

        }
        if (savedInstanceState != null) {
            lastPos = savedInstanceState.getLong(PLAYER_POSITON_KEY);
        } else {
            lastPos = 0;
        }

    }


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        exoPlayer = view.findViewById(R.id.exo_player);
        fabNext = view.findViewById(R.id.fab_forward);
        fabPrevious = view.findViewById(R.id.fab_backward);
        stepDescription = view.findViewById(R.id.step_description);
        stepDescriptionDetails = view.findViewById(R.id.step_description_details);
        videoNotAvailable = view.findViewById(R.id.tv_no_video);
        stepDescriptionDetails.setText(step.getDescription());
        checkForFabs();
        setFabClickListeners();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            full();
        } else {
            normal();
        }
        return view;
    }

    private void setFabClickListeners() {

        fabNext.setOnClickListener(v -> {
                    if (!((position + 1) > (recipe.getSteps().size() - 1)) ) {
                        if (getResources().getBoolean(R.bool.twoPane)) {
                            ((RecipeDetailsActivity) getActivity()).naigateView(position + 1);
                        }
                        else {
                            ((StepDetailActivity) getActivity()).loadFragement(position + 1);
                        }
                    }
                    else {
                        Toast.makeText(requireActivity(), "Reached the end", Toast.LENGTH_LONG);
                    }
                }
        );

        fabPrevious.setOnClickListener(v -> {
                    if (!(position - 1 < 0) ) {
                        if (getResources().getBoolean(R.bool.twoPane)) {
                            ((RecipeDetailsActivity) getActivity()).naigateView(position - 1);
                        }
                        else {
                            ((StepDetailActivity) getActivity()).loadFragement(position - 1);
                        }
                    }
                    else {
                        Toast.makeText(requireActivity(), "Reached the beginning", Toast.LENGTH_LONG);
                    }
                }
        );
        checkForFabs();
    }

    private void normal() {
        stepDescription.setVisibility(View.VISIBLE);
        stepDescriptionDetails.setVisibility(View.VISIBLE);
        fabNext.setVisibility(View.VISIBLE);
        fabPrevious.setVisibility(View.VISIBLE);

        if (!getResources().getBoolean(R.bool.twoPane)){
            android.view.ViewGroup.LayoutParams params =  exoPlayer.getLayoutParams();
            params.width= ViewGroup.LayoutParams.MATCH_PARENT;
            params.height=600;
            ((StepDetailActivity) getActivity()).removeImmersive();
            exoPlayer.setLayoutParams(params);
        }
    }

    private void full() {
        fabNext.setVisibility(View.INVISIBLE);
        fabPrevious.setVisibility(View.INVISIBLE);

        if (!getResources().getBoolean(R.bool.twoPane)){
            android.view.ViewGroup.LayoutParams params =  exoPlayer.getLayoutParams();
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
            params.height = FrameLayout.LayoutParams.MATCH_PARENT;
            ((StepDetailActivity) getActivity()).setImmersive();
            exoPlayer.setLayoutParams(params);
        }


    }

    private void checkForFabs() {
        if ( position + 1 > recipe.getSteps().size() - 1 ){
            fabNext.setVisibility(View.GONE);
        }
        else {
            fabNext.setVisibility(View.VISIBLE);
        }
        if (position - 1 < 0) {
            fabPrevious.setVisibility(View.GONE);
        }else {
            fabPrevious.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onResume() {
        super.onResume();
        startExoPlayer();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation ==  Configuration.ORIENTATION_LANDSCAPE){
            full();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
         normal();
        }
    }

    private void startExoPlayer() {

        if (mExoPlayer == null && !(videoUrl == null) ) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(requireActivity(), trackSelector);
            exoPlayer.setPlayer(mExoPlayer);
            mExoPlayer.addListener(this);
            String userAgent = Util.getUserAgent(requireActivity(), getString(R.string.app_name));
            DataSource.Factory factory = new DefaultDataSourceFactory(requireActivity(), userAgent);
            MediaSource mediaSource =
                    new ExtractorMediaSource.Factory(factory).createMediaSource(Uri.parse(videoUrl));
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.seekTo(lastPos);
            mExoPlayer.setPlayWhenReady(true);
            exoPlayer.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        }

        else {
            videoNotAvailable.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITON_KEY, lastPos);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            lastPos = mExoPlayer.getCurrentPosition();
            releasePlayer();
        }
    }

    void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}