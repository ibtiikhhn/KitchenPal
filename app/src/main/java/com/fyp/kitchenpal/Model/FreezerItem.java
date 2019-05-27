package com.fyp.kitchenpal.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "freezerTable")
public class FreezerItem {

    @PrimaryKey(autoGenerate = true)
    int id;
    String freezerItem;
    Long startDate;
    Long expiryDate;
    String category;
    String bytes;

    public FreezerItem(String freezerItem, long startDate, long expiryDate, String bytes) {
        this.freezerItem = freezerItem;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
//        this.category = category;
        this.bytes = bytes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public String getFreezerItem() {
        return freezerItem;
    }

    public void setShoppingName(String freezerItem) {
        this.freezerItem= freezerItem;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
