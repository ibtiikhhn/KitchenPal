package com.fyp.kitchenpal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.kitchenpal.Model.Recipe;
import com.fyp.kitchenpal.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    Context context;
    List<Recipe> recipeList;
    OnItemClick onItemClick;

    public RecipeAdapter(Context context, List<Recipe> recipeList, OnItemClick onItemClick) {
        this.context = context;
        this.recipeList = recipeList;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_cardview, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        final Float avgRating = recipeList.get(position).getRating();
        holder.recipeNameTV.setText(recipeList.get(position).getRecipeName());
        holder.recipeMethodTV.setText(recipeList.get(position).getRecipeMethod());
        Glide.with(context).load(recipeList.get(position).getImageURl()).into(holder.recipeImage);
        if (recipeList.get(position).getRating() == null) {
            holder.ratingBar.setRating(0f);
        } else {
            holder.ratingBar.setRating(recipeList.get(position).getRating());
        }

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeNameTV;
        TextView recipeMethodTV;
        ImageView recipeImage;
        AppCompatRatingBar ratingBar;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeNameTV = itemView.findViewById(R.id.recipeNameTV);
            recipeMethodTV = itemView.findViewById(R.id.recipeMethod);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClick.onCLick(position);
                        }
                    }
                }
            });

        }
    }
}
