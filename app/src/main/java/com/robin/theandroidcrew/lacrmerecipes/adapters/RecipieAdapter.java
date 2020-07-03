package com.robin.theandroidcrew.lacrmerecipes.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;

import java.util.List;

public class RecipieAdapter extends RecyclerView.Adapter<RecipieAdapter.ViewHolder> {
    private List<Recipe> items;
    private OnItemClick clickListener;

    public RecipieAdapter(List<Recipe> items, OnItemClick clickListener) {
        this.items = items;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipes_item_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe item = items.get(position);
        holder.bind(item, position);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeName = itemView.findViewById(R.id.recipeName);
        }

        public void bind(Recipe item, int position) {
            recipeName.setText(item.getName());
            itemView.setOnClickListener(v -> {
                  clickListener.OnClick(position);
                }
            );
        }
    }

    public interface OnItemClick{
        void OnClick(int position);
    }

}