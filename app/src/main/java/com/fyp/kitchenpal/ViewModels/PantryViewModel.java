package com.fyp.kitchenpal.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.PantryItem;
import com.fyp.kitchenpal.Model.ShoppingItem;
import com.fyp.kitchenpal.NotificationManager;
import com.fyp.kitchenpal.Repository.PantryCalls;
import com.fyp.kitchenpal.Repository.ShoppingCalls;

import java.util.List;

public class PantryViewModel extends AndroidViewModel {

    PantryCalls pantryCalls;
    ShoppingCalls shoppingCalls;
    NotificationManager notificationManager;

    public PantryViewModel(@NonNull Application application) {
        super(application);
        pantryCalls = new PantryCalls(application);
        shoppingCalls = new ShoppingCalls(application);

    }

    public void insertItem(PantryItem pantryItem) {
        pantryCalls.insert(pantryItem);
    }

    public void deleteItem(PantryItem pantryItem) {
        pantryCalls.delete(pantryItem);
        shoppingCalls.insert(convert(pantryItem));
    }

    public void updateItem(PantryItem pantryItem) {
        pantryCalls.update(pantryItem);
    }

    public void deleteAllItems() {
        pantryCalls.deleteAll();
    }

    public LiveData<List<PantryItem>> getAllItems() {
        return pantryCalls.getPantryItems();
    }

    public ShoppingItem convert(PantryItem pantryItem) {
        ShoppingItem shoppingItem = new ShoppingItem(pantryItem.getPantryName());
        return shoppingItem;
    }

}
