package com.fyp.kitchenpal.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pantryTable")
public class PantryItem {

    @PrimaryKey(autoGenerate = true)
    int id;
    String pantryName;
    long startDate;
    long expiryDate;
    String bytes;

    public PantryItem(String pantryName, long startDate, long expiryDate,String bytes) {
        this.pantryName = pantryName;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.bytes = bytes;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPantryName() {
        return pantryName;
    }

    public void setPantryName(String pantryName) {
        this.pantryName = pantryName;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }
}
