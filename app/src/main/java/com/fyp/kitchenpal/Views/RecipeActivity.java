package com.fyp.kitchenpal.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Adapters.OnItemClick;
import com.fyp.kitchenpal.Adapters.RecipeAdapter;
import com.fyp.kitchenpal.Model.Recipe;
import com.fyp.kitchenpal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements OnItemClick {

    public static final String TAG = "RecipeActivity";
    RecyclerView recipeRV;
    RecipeAdapter recipeAdapter;
    List<Recipe> recipeList;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("recipes");
        recipeRV = findViewById(R.id.recipeRV);
        recipeAdapter = new RecipeAdapter(this, new ArrayList<Recipe>(),this);
        recipeRV.setLayoutManager(new LinearLayoutManager(this));
        recipeRV.setAdapter(recipeAdapter);

    }


    //get the recipe list from the firebase
    @Override
    protected void onStart() {
        super.onStart();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
                recipeAdapter.setRecipeList(recipeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RecipeActivity.this, databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onCLick(final int position) {
        Recipe recipe = recipeList.get(position);

        final AlertDialog dialogBuilder = new AlertDialog.Builder(RecipeActivity.this).create();
        LayoutInflater inflater = RecipeActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.recipe_dialogue, null);

        TextView recipeName = dialogView.findViewById(R.id.recipeNameTV);
        TextView recipeMethod = dialogView.findViewById(R.id.recipeMethod);
        Button dissmisBT = dialogView.findViewById(R.id.dismissBT);
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBarDialogue);
        ListView listView = dialogView.findViewById(R.id.ingredientsLV);

        String[] name = new String[recipe.getIngredients().size()];
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            name[i] = recipe.getIngredients().get(i).getName();
        }

        ArrayAdapter listAdapter = new ArrayAdapter<String>(RecipeActivity.this, R.layout.ingredienttv,name);
        listView.setAdapter(listAdapter);
        recipeName.setText(recipe.getRecipeName());
        recipeMethod.setText("RECIPE METHOD : " + recipe.getRecipeMethod());

        dissmisBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Float finalRating = (rating+recipeList.get(position).getRating())/2;
                String iddd = recipeList.get(position).getRecipeName();

                reference.child(iddd+"/rating").setValue(finalRating);
            }
        });


        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
