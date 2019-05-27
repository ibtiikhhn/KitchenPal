package com.fyp.kitchenpal.Repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fyp.kitchenpal.Model.ShoppingItem;

import java.util.List;

@Dao
public interface ShoppingDao {
    @Insert
    void insert(ShoppingItem shoppingItem);

    @Update
    void update(ShoppingItem shoppingItem);

    @Delete
    void delete(ShoppingItem shoppingItem);

    @Query("DELETE from shoppingTable")
    void deleteAll();

    @Query("SELECT * from shoppingTable")
    LiveData<List<ShoppingItem>> getAllShoppingItems();
}
