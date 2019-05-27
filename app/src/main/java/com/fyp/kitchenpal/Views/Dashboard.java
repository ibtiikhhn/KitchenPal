package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.fyp.kitchenpal.R;

public class Dashboard extends AppCompatActivity {

    CardView fridgeCV;
    CardView listCV;
    CardView freezerCV;
    CardView profileCV;
    CardView pantryCV;
    CardView recipyCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fridgeCV = findViewById(R.id.Fridge);
        freezerCV = findViewById(R.id.Freezer);
        pantryCV = findViewById(R.id.Pantry);
        listCV = findViewById(R.id.Shoppinglist);
        profileCV = findViewById(R.id.Profile);
        recipyCV = findViewById(R.id.Recipe);

        profileCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        recipyCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, RecipeActivity.class);
                startActivity(intent);
            }
        });

        listCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, ShoppingActivity.class);
                startActivity(intent);
            }
        });

        fridgeCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, FridgeActivity.class);
                startActivity(intent);
            }
        });

        pantryCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, PantryActivity.class);
                startActivity(intent);
            }
        });

        freezerCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, FreezerActivity.class);
                startActivity(intent);
            }
        });

    }
}
