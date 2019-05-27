package com.fyp.kitchenpal.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.FridgeItem;

import java.util.List;

public class FridgeCalls {

    private FridgeDao fridgeDao;
    LiveData<List<FridgeItem>> fridgeItems;

    public FridgeCalls(Application application) {
        MyDatabase myDatabase = MyDatabase.getDatabase(application);
        fridgeDao = myDatabase.fridgeDao();
        fridgeItems = fridgeDao.getAllFridgeItems();
    }

    public void insert(FridgeItem fridgeItem) {
        new InsertItem(fridgeDao).execute(fridgeItem);
    }

    public void update(FridgeItem fridgeItem) {
        new UpdateItem(fridgeDao).execute(fridgeItem);
    }

    public void delete(FridgeItem fridgeItem) {
        new DeleteItem(fridgeDao).execute(fridgeItem);
    }

    public void deleteAll() {
        new DeleteAllItems(fridgeDao).execute();
    }

    public LiveData<List<FridgeItem>> getFridgeItems() {
        return fridgeItems;
    }

    public static class InsertItem extends AsyncTask<FridgeItem, Void, Void> {

        FridgeDao fridgeDao;

        InsertItem(FridgeDao fridgeDao) {
            this.fridgeDao = fridgeDao;
        }

        @Override
        protected Void doInBackground(FridgeItem... fridgeItems) {
            fridgeDao.insert(fridgeItems[0]);
            return null;
        }
    }

    public static class UpdateItem extends AsyncTask<FridgeItem, Void, Void> {

        FridgeDao fridgeDao;

        UpdateItem(FridgeDao fridgeDao) {
            this.fridgeDao = fridgeDao;
        }

        @Override
        protected Void doInBackground(FridgeItem... fridgeItems) {
            fridgeDao.update(fridgeItems[0]);
            return null;
        }
    }

    public static class DeleteItem extends AsyncTask<FridgeItem, Void, Void> {

        FridgeDao fridgeDao;

        public DeleteItem(FridgeDao fridgeDao) {
            this.fridgeDao = fridgeDao;
        }

        @Override
        protected Void doInBackground(FridgeItem... fridgeItems) {
            fridgeDao.delete(fridgeItems[0]);
            return null;
        }
    }

    public static class DeleteAllItems extends AsyncTask<FridgeItem, Void, Void> {

        FridgeDao fridgeDao;

        DeleteAllItems(FridgeDao fridgeDao) {
            this.fridgeDao = fridgeDao;
        }

        @Override
        protected Void doInBackground(FridgeItem... fridgeItems) {
            fridgeDao.deleteAll();
            return null;
        }
    }

}
