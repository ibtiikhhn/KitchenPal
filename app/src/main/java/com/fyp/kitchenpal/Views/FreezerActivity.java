package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Adapters.FreezerAdapter;
import com.fyp.kitchenpal.Model.FreezerItem;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.ViewModels.DateConverter;
import com.fyp.kitchenpal.ViewModels.FreezerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FreezerActivity extends AppCompatActivity {

    FreezerAdapter adapter;
    RecyclerView recyclerView;
    List<FreezerItem> items;
    FreezerViewModel viewModel;
    DateConverter dateConverter;
    FloatingActionButton addItem;
    FloatingActionButton deleteAllFreezerItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freezer);
        initViews();

        viewModel = ViewModelProviders.of(this).get(FreezerViewModel.class);
        viewModel.getAllItems().observe(this, new Observer<List<FreezerItem>>() {
            @Override
            public void onChanged(List<FreezerItem> freezerItems) {
                adapter.addItems(freezerItems);
            }
        });

        deleteAllFreezerItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllItems();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FreezerActivity.this, AddItemActivity.class);
                intent.putExtra("from", "FreezerActivity");
                startActivity(intent);
            }
        });
    }

    public void initViews() {
        addItem = findViewById(R.id.addItemBT);
        recyclerView = findViewById(R.id.freezerItems);
        deleteAllFreezerItems = findViewById(R.id.deleteAllFreezerItemsBT);
        items = new ArrayList<>();
        dateConverter = new DateConverter(new SimpleDateFormat("dd-MM-YYYY"));
        adapter = new FreezerAdapter(new ArrayList<FreezerItem>(), this, dateConverter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }

    public void deleteFoodItem(FreezerItem freezerItem) {
        viewModel.deleteItem(freezerItem);
//        Log.i(TAG, "deleteFoodItem: "+fridgeItem.getFridgeName());
    }
}
