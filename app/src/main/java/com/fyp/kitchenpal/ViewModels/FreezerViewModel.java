package com.fyp.kitchenpal.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.FreezerItem;
import com.fyp.kitchenpal.Model.ShoppingItem;
import com.fyp.kitchenpal.Repository.FreezerCalls;
import com.fyp.kitchenpal.Repository.ShoppingCalls;

import java.util.List;

public class FreezerViewModel extends AndroidViewModel {

    FreezerCalls freezerCalls;
    ShoppingCalls shoppingCalls;

    public FreezerViewModel(@NonNull Application application) {
        super(application);
        freezerCalls = new FreezerCalls(application);
        shoppingCalls = new ShoppingCalls(application);
    }

    public void insertItem(FreezerItem freezerItem) {
        freezerCalls.insert(freezerItem);
    }

    public void updateItem(FreezerItem freezerItem) {
        freezerCalls.update(freezerItem);
    }

    public void deleteItem(FreezerItem freezerItem) {
        freezerCalls.delete(freezerItem);
        shoppingCalls.insert(convert(freezerItem));
    }

    public LiveData<List<FreezerItem>> getAllItems() {
        return freezerCalls.getFreezerItems();
    }

    public void deleteAllItems() {
        freezerCalls.deleteAll();
    }

    public ShoppingItem convert(FreezerItem freezerItem) {
        ShoppingItem shoppingItem = new ShoppingItem(freezerItem.getFreezerItem());
        return shoppingItem;
    }

}
