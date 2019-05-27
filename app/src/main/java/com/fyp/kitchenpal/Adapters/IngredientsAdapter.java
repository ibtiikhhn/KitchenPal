package com.fyp.kitchenpal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Model.Ingredients;
import com.fyp.kitchenpal.R;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    Context context;
    List<Ingredients> ingredients;

    public IngredientsAdapter(Context context, List<Ingredients> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_cardview, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.ingredientTV.setText(ingredients.get(position).getName());
        holder.ingredientNUM.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(List<Ingredients> list) {
        this.ingredients = list;
        notifyDataSetChanged();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientTV;
        TextView ingredientNUM;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientNUM = itemView.findViewById(R.id.ingredientNumber);
            ingredientTV = itemView.findViewById(R.id.ingredientNamee);
        }
    }
}
