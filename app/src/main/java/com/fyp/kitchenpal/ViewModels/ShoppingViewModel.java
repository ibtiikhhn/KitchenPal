package com.fyp.kitchenpal.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.ShoppingItem;
import com.fyp.kitchenpal.Repository.ShoppingCalls;

import java.util.ArrayList;
import java.util.List;

public class ShoppingViewModel extends AndroidViewModel {

    ShoppingCalls shoppingCalls;
    List<ShoppingItem> shoppingItems;

    public ShoppingViewModel(@NonNull Application application) {
        super(application);
        shoppingCalls = new ShoppingCalls(application);
        shoppingItems = new ArrayList<>();
    }

    public void insertItem(ShoppingItem shoppingItem) {
        shoppingCalls.insert(shoppingItem);
    }

    public void deleteItem(ShoppingItem shoppingItem) {
        shoppingCalls.delete(shoppingItem);
    }

    public void updateItem(ShoppingItem shoppingItem) {
        shoppingCalls.update(shoppingItem);
    }

    public void deleteAll() {
        shoppingCalls.deleteAll();
    }

    public LiveData<List<ShoppingItem>> getShoppingItems() {
        return shoppingCalls.getShoppingItems();
    }

}
