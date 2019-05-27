package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Adapters.FridgeAdapter;
import com.fyp.kitchenpal.Model.FridgeItem;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.ViewModels.DateConverter;
import com.fyp.kitchenpal.ViewModels.FridgeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FridgeActivity extends AppCompatActivity {

    public static final String TAG = "FridgeActivity";

    FridgeAdapter adapter;
    RecyclerView recyclerView;
    List<FridgeItem> items;
    FridgeViewModel viewModel;
    DateConverter dateConverter;
    FloatingActionButton addItem;
    FloatingActionButton deleteAllitemsBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        initViews();

        viewModel = ViewModelProviders.of(this).get(FridgeViewModel.class);
        viewModel.getAllItems().observe(this, new Observer<List<FridgeItem>>() {
            @Override
            public void onChanged(List<FridgeItem> fridgeItems) {
//                items.addAll(fridgeItems);
                adapter.addItems(fridgeItems);
//                adapter.notifyDataSetChanged();
            }
        });

        deleteAllitemsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAll();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FridgeActivity.this, AddItemActivity.class);
                intent.putExtra("from", "FridgeActivity");
                startActivity(intent);
            }
        });
    }

    public void initViews() {
        addItem = findViewById(R.id.addItemBT);
        recyclerView = findViewById(R.id.fridgeItems);
        deleteAllitemsBT = findViewById(R.id.deleteAllFridgeItemsBT);
        items = new ArrayList<>();
        dateConverter = new DateConverter(new SimpleDateFormat("dd-MM-YYYY"));
        adapter = new FridgeAdapter(new ArrayList<FridgeItem>(), this,dateConverter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);
    }

    public void deleteFoodItem(FridgeItem fridgeItem) {
        viewModel.deleteItem(fridgeItem);
        Log.i(TAG, "deleteFoodItem: "+fridgeItem.getFridgeName());
    }

}
