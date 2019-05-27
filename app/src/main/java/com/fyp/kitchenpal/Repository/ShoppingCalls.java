package com.fyp.kitchenpal.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.ShoppingItem;

import java.util.List;

public class ShoppingCalls {
    private ShoppingDao shoppingDao;
    LiveData<List<ShoppingItem>> shoppingItems;

    public ShoppingCalls(Application application) {
        MyDatabase myDatabase = MyDatabase.getDatabase(application);
        shoppingDao = myDatabase.shoppingDao();
        shoppingItems = shoppingDao.getAllShoppingItems();
    }

    public void insert(ShoppingItem shoppingItem) {
        new ShoppingCalls.InsertItem(shoppingDao).execute(shoppingItem);
    }

    public void update(ShoppingItem shoppingItem) {
        new ShoppingCalls.UpdateItem(shoppingDao).execute(shoppingItem);
    }

    public void delete(ShoppingItem shoppingItem) {
        new ShoppingCalls.DeleteItem(shoppingDao).execute(shoppingItem);
    }

    public void deleteAll() {
        new ShoppingCalls.DeleteAllItems(shoppingDao).execute();
    }

    public LiveData<List<ShoppingItem>> getShoppingItems() {
        return shoppingItems;
    }

    public static class InsertItem extends AsyncTask<ShoppingItem, Void, Void> {

        ShoppingDao shoppingDao;

        InsertItem(ShoppingDao shoppingDao) {
            this.shoppingDao = shoppingDao;
        }

        @Override
        protected Void doInBackground(ShoppingItem... shoppingItems) {
            shoppingDao.insert(shoppingItems[0]);
            return null;
        }
    }

    public static class UpdateItem extends AsyncTask<ShoppingItem, Void, Void> {

        ShoppingDao shoppingDao;

        UpdateItem(ShoppingDao shoppingDao) {
            this.shoppingDao = shoppingDao;
        }

        @Override
        protected Void doInBackground(ShoppingItem... shoppingItems) {
            shoppingDao.update(shoppingItems[0]);
            return null;
        }
    }

    public static class DeleteItem extends AsyncTask<ShoppingItem, Void, Void> {

        ShoppingDao shoppingDao;

        DeleteItem(ShoppingDao shoppingDao) {
            this.shoppingDao = shoppingDao;
        }

        @Override
        protected Void doInBackground(ShoppingItem... shoppingItems) {
            shoppingDao.delete(shoppingItems[0]);
            return null;
        }
    }

    public static class DeleteAllItems extends AsyncTask<ShoppingItem, Void, Void> {

        ShoppingDao shoppingDao;

        DeleteAllItems(ShoppingDao shoppingDao) {
            this.shoppingDao = shoppingDao;
        }

        @Override
        protected Void doInBackground(ShoppingItem... shoppingItems) {
            shoppingDao.deleteAll();
            return null;
        }
    }
}
