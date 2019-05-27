package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Adapters.OnItemClick;
import com.fyp.kitchenpal.Adapters.RecipeAdapter;
import com.fyp.kitchenpal.Model.Recipe;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.Repository.SharedPrefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity implements OnItemClick {


    RecyclerView recyclerView;
    FloatingActionButton addRecipe;
    List<Recipe> recipeList;
    RecipeAdapter adapter;
    Toolbar toolbar;
    SharedPrefs sharedPrefs;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        sharedPrefs = new SharedPrefs(this);
        toolbar = findViewById(R.id.adminToolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.admin_menu);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.ic_more_vert_black_24dp));
        recyclerView = findViewById(R.id.adminRV);
        addRecipe = findViewById(R.id.addRecipeBT);
        recipeList = new ArrayList<>();
        adapter = new RecipeAdapter(this, new ArrayList<Recipe>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference("recipes");

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

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
                adapter.setRecipeList(recipeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AdminActivity.this, databaseError.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adminLogout:
                sharedPrefs.loginAsAdmin(false);
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onCLick(final int position) {
        final Recipe recipe = recipeList.get(position);

        final AlertDialog dialogBuilder = new AlertDialog.Builder(AdminActivity.this).create();
        LayoutInflater inflater = AdminActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_dialogue, null);

        Button delBT = dialogView.findViewById(R.id.dell);
        Button viewRecipe = dialogView.findViewById(R.id.viewRecipee);

        viewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = recipeList.get(position);

                final AlertDialog dialogBuilder = new AlertDialog.Builder(AdminActivity.this).create();
                LayoutInflater inflater = AdminActivity.this.getLayoutInflater();
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

                ArrayAdapter listAdapter = new ArrayAdapter<String>(AdminActivity.this, R.layout.ingredienttv, name);
                listView.setAdapter(listAdapter);
                recipeName.setText(recipe.getRecipeName());
                recipeMethod.setText("RECIPE METHOD : " + recipe.getRecipeMethod());

                dissmisBT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                });

                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }
        });

        delBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String iddd = recipe.getRecipeName();
                reference.child(iddd).removeValue();
                dialogBuilder.dismiss();
            }
        });


        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
