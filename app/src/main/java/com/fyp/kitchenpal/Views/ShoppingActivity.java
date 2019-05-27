package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Adapters.ShoppingAdapter;
import com.fyp.kitchenpal.Model.ShoppingItem;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.ViewModels.DateConverter;
import com.fyp.kitchenpal.ViewModels.ShoppingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingActivity extends AppCompatActivity {

    public static final String TAG = "ShoppingActivity";
    RecyclerView shoppingRV;
    ShoppingAdapter adapter;
    DateConverter dateConverter;
    ShoppingViewModel shoppingViewModel;
    List<ShoppingItem> shoppingItemList;
    FloatingActionButton addItem;
    FloatingActionButton deleteAllItemsBT;
    FloatingActionButton shareAsMessageBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);


        addItem = findViewById(R.id.addShoppingItem);
        deleteAllItemsBT = findViewById(R.id.deleteAllShoppingItems);
        shoppingRV = findViewById(R.id.shoppingRV);
        shareAsMessageBT = findViewById(R.id.shareAsMessagebt);
        shoppingItemList = new ArrayList<>();

        shoppingViewModel = ViewModelProviders.of(this).get(ShoppingViewModel.class);

        dateConverter = new DateConverter(new SimpleDateFormat("dd-MM-YYYY"));
        adapter = new ShoppingAdapter(new ArrayList<ShoppingItem>(),this);
        shoppingRV.setLayoutManager(new LinearLayoutManager(this));
        shoppingRV.setAdapter(adapter);

        shoppingViewModel.getShoppingItems().observe(this, new Observer<List<ShoppingItem>>() {
            @Override
            public void onChanged(List<ShoppingItem> shoppingItems) {
//                shoppingItemList.addAll(shoppingItems);
                adapter.addItems(shoppingItems);
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialogBuilder = new AlertDialog.Builder(ShoppingActivity.this).create();
                LayoutInflater inflater = ShoppingActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.shopping_item_dialogue, null);

                final EditText shoppingItemET = dialogView.findViewById(R.id.shoppingItemET);
                Button addBt = dialogView.findViewById(R.id.saveshopppingItemBT);


                addBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = shoppingItemET.getText().toString().trim();
//                        String message = messageET.getText().toString().trim();

                        if (name.isEmpty()) {
                            shoppingItemET.setError("This Can't be empty");
                            return;
                        }
                        ShoppingItem shoppingItem = new ShoppingItem(name);
                        shoppingViewModel.insertItem(shoppingItem);
                        dialogBuilder.dismiss();
                    }
                });
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
            }
        });

        deleteAllItemsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingViewModel.deleteAll();
            }
        });

        shareAsMessageBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: ");
                final StringBuilder message = new StringBuilder("Shopping List");
                shoppingViewModel.getShoppingItems().observe(ShoppingActivity.this, new Observer<List<ShoppingItem>>() {
                    @Override
                    public void onChanged(List<ShoppingItem> shoppingItems) {

                        for (ShoppingItem shoppingItem : shoppingItems) {
                            message.append("\n").append(shoppingItem.getItemName());
                        }
                        Log.i(TAG, "ff"+message.toString());
                    }
                });
                sendSms(message.toString());

            }
        });



    }

    public void deleteItem(ShoppingItem shoppingItem) {
        shoppingViewModel.deleteItem(shoppingItem);
    }

    public void sendSms(String message) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

}
