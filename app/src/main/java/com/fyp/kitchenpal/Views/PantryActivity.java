package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Adapters.PantryAdapter;
import com.fyp.kitchenpal.Model.PantryItem;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.ViewModels.DateConverter;
import com.fyp.kitchenpal.ViewModels.PantryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PantryActivity extends AppCompatActivity {

    PantryAdapter adapter;
    RecyclerView recyclerView;
    List<PantryItem> items;
    PantryViewModel viewModel;
    DateConverter dateConverter;
    FloatingActionButton addItem;
    FloatingActionButton deleteAllItemsBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        initViews();

        viewModel = ViewModelProviders.of(this).get(PantryViewModel.class);
        viewModel.getAllItems().observe(this, new Observer<List<PantryItem>>() {
            @Override
            public void onChanged(List<PantryItem> pantryItems) {
//                items.addAll(fridgeItems);
                adapter.addItems(pantryItems);
//                adapter.notifyDataSetChanged();
            }
        });

        deleteAllItemsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllItems();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantryActivity.this, AddItemActivity.class);
                intent.putExtra("from", "PantryActivity");
                startActivity(intent);
            }
        });

    }

    public void initViews() {
        addItem = findViewById(R.id.addItemBT);
        recyclerView = findViewById(R.id.pantryItems);
        deleteAllItemsBT = findViewById(R.id.deleteAllPantryItemsBT);
        items = new ArrayList<>();
        dateConverter = new DateConverter(new SimpleDateFormat("dd-MM-YYYY"));
        adapter = new PantryAdapter(new ArrayList<PantryItem>(), this, dateConverter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }

    public void deleteFoodItem(PantryItem pantryItem) {
        viewModel.deleteItem(pantryItem);
//        Log.i(TAG, "deleteFoodItem: "+fridgeItem.getFridgeName());
    }
}
