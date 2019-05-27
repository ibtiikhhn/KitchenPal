package com.fyp.kitchenpal.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fyp.kitchenpal.Model.FreezerItem;

import java.util.List;

@Dao
public interface FreezerDao {
    @Insert
    void insert(FreezerItem freezerItem);

    @Update
    void update(FreezerItem freezerItem);

    @Delete
    void delete(FreezerItem freezerItem);

    @Query("DELETE from freezerTable")
    void deleteAll();

    @Query("SELECT * from freezerTable")
    LiveData<List<FreezerItem>> getAllFreezerItems();
}
