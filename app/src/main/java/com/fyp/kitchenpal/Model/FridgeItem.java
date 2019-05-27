package com.fyp.kitchenpal.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fridgeTable")
public class FridgeItem {

    @PrimaryKey(autoGenerate = true)
    int id;
    String fridgeName;
    long startDate;
    long expiryDate;
    String bytes;

    public FridgeItem(String fridgeName, long startDate, long expiryDate,String bytes) {
        this.fridgeName = fridgeName;
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

    public String getFridgeName() {
        return fridgeName;
    }

    public void setFridgeName(String fridgeName) {
        this.fridgeName = fridgeName;
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
