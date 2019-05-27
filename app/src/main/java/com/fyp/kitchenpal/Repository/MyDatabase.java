package com.fyp.kitchenpal.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.fyp.kitchenpal.Model.FreezerItem;
import com.fyp.kitchenpal.Model.FridgeItem;
import com.fyp.kitchenpal.Model.PantryItem;
import com.fyp.kitchenpal.Model.ShoppingItem;

@Database(entities = {FridgeItem.class, FreezerItem.class, PantryItem.class, ShoppingItem.class}, version = 7,exportSchema = false )
public abstract class MyDatabase extends RoomDatabase {

    public static MyDatabase myDatabase;
    public abstract FridgeDao fridgeDao();
    public abstract FreezerDao freezerDao();
    public abstract PantryDao pantryDao();
    public abstract ShoppingDao shoppingDao();

    public static synchronized MyDatabase getDatabase(Context context) {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "MainDB")
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return myDatabase;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

}
