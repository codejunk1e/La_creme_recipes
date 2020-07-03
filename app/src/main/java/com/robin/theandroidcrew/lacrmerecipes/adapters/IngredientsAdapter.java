package com.robin.theandroidcrew.lacrmerecipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.models.Ingredient;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> items;
    private OnItemClick clickListener;

    public IngredientsAdapter(List<Ingredient> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient item = items.get(position);
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
        TextView ingredientName;
        TextView quantity;
        TextView measure;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            quantity = itemView.findViewById(R.id.textView_qty);
            measure = itemView.findViewById(R.id.textView_measure);

        }

        public void bind(Ingredient item, int position) {
            ingredientName.setText(item.getIngredient());
            quantity.setText(String.valueOf(item.getQuantity()));
            measure.setText(item.getMeasure());

            itemView.setOnClickListener(v -> {
                }
            );
        }
    }

    public interface OnItemClick{
        void OnClick(int position);
    }

}