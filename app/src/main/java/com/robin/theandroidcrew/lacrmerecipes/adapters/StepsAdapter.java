package com.robin.theandroidcrew.lacrmerecipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.robin.theandroidcrew.lacrmerecipes.R;
import com.robin.theandroidcrew.lacrmerecipes.models.Recipe;
import com.robin.theandroidcrew.lacrmerecipes.models.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    private List<Step> items;
    private OnItemClick clickListener;

    public StepsAdapter(List<Step> items, OnItemClick clickListener) {
        this.items = items;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_item_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Step item = items.get(position);
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
        TextView stepName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepName = itemView.findViewById(R.id.step_name);
        }

        public void bind(Step item, int position) {
            stepName.setText(item.getShortDescription());

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