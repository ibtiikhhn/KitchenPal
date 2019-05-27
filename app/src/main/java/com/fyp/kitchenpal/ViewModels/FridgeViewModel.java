package com.fyp.kitchenpal.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.FridgeItem;
import com.fyp.kitchenpal.Model.ShoppingItem;
import com.fyp.kitchenpal.Repository.FridgeCalls;
import com.fyp.kitchenpal.Repository.ShoppingCalls;

import java.util.List;

public class FridgeViewModel extends AndroidViewModel {

    FridgeCalls fridgeCalls;
    ShoppingCalls shoppingCalls;

    public FridgeViewModel(@NonNull Application application) {
        super(application);
        fridgeCalls = new FridgeCalls(application);
        shoppingCalls = new ShoppingCalls(application);
    }

    public void insertItem(FridgeItem fridgeItem) {
        fridgeCalls.insert(fridgeItem);
    }

    public void deleteItem(FridgeItem fridgeItem) {
        fridgeCalls.delete(fridgeItem);
        shoppingCalls.insert(convert(fridgeItem));
    }

    public void update(FridgeItem fridgeItem) {
        fridgeCalls.update(fridgeItem);
    }

    public void deleteAll() {
        fridgeCalls.deleteAll();
    }

    public LiveData<List<FridgeItem>> getAllItems() {
        return fridgeCalls.getFridgeItems();
    }

    public ShoppingItem convert(FridgeItem fridgeItem) {
        ShoppingItem shoppingItem = new ShoppingItem(fridgeItem.getFridgeName());
        return shoppingItem;
    }


}
