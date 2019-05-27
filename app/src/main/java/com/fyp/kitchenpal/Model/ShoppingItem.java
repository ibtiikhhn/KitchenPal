package com.fyp.kitchenpal.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "shoppingTable")
public class ShoppingItem {

    @PrimaryKey(autoGenerate = true)
    int id;
    String itemName;

    public ShoppingItem(String itemName) {
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
