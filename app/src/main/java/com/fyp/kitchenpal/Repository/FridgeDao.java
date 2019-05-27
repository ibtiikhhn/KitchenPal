package com.fyp.kitchenpal.Repository;

import com.fyp.kitchenpal.Model.FridgeItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FridgeDao {

    @Insert
    void insert(FridgeItem fridgeItem);

    @Update
    void update(FridgeItem fridgeItem);

    @Delete
    void delete(FridgeItem fridgeItem);

    @Query("DELETE from fridgeTable")
    void deleteAll();

    @Query("SELECT * from fridgeTable")
    LiveData<List<FridgeItem>> getAllFridgeItems();
}
