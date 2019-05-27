package com.fyp.kitchenpal.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fyp.kitchenpal.Model.PantryItem;

import java.util.List;

@Dao
public interface PantryDao {
    @Insert
    void insert(PantryItem pantryItem);

    @Update
    void update(PantryItem pantryItem);

    @Delete
    void delete(PantryItem pantryItem);

    @Query("DELETE from pantryTable")
    void deleteAll();

    @Query("SELECT * from pantryTable")
    LiveData<List<PantryItem>> getAllPantryItems();
}
