package com.fyp.kitchenpal.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.PantryItem;

import java.util.List;

public class PantryCalls {

    private PantryDao pantryDao;
    LiveData<List<PantryItem>> pantryItems;

    public PantryCalls(Application application) {
        MyDatabase myDatabase = MyDatabase.getDatabase(application);
        pantryDao = myDatabase.pantryDao();
        pantryItems = pantryDao.getAllPantryItems();
    }

    public void insert(PantryItem pantryItem) {
        new InsertItem(pantryDao).execute(pantryItem);
    }

    public void update(PantryItem pantryItem) {
        new UpdateItem(pantryDao).execute(pantryItem);
    }

    public void delete(PantryItem pantryItem) {
        new DeleteItem(pantryDao).execute(pantryItem);
    }

    public void deleteAll() {
        new DeleteAllItems(pantryDao).execute();
    }

    public LiveData<List<PantryItem>> getPantryItems() {
        return pantryItems;
    }

    public static class InsertItem extends AsyncTask<PantryItem, Void, Void> {

        PantryDao pantryDao;

        InsertItem(PantryDao pantryDao) {
            this.pantryDao = pantryDao;
        }

        @Override
        protected Void doInBackground(PantryItem... pantryItems) {
            pantryDao.insert(pantryItems[0]);
            return null;
        }
    }

    public static class UpdateItem extends AsyncTask<PantryItem, Void, Void> {

        PantryDao pantryDao;

        UpdateItem(PantryDao pantryDao) {
            this.pantryDao = pantryDao;
        }

        @Override
        protected Void doInBackground(PantryItem... pantryItems) {
            pantryDao.update(pantryItems[0]);
            return null;
        }
    }

    public static class DeleteItem extends AsyncTask<PantryItem, Void, Void> {

        PantryDao pantryDao;

        DeleteItem(PantryDao pantryDao) {
            this.pantryDao = pantryDao;
        }

        @Override
        protected Void doInBackground(PantryItem... pantryItems) {
            pantryDao.delete(pantryItems[0]);
            return null;
        }
    }

    public static class DeleteAllItems extends AsyncTask<PantryItem, Void, Void> {

        PantryDao pantryDao;

        DeleteAllItems(PantryDao pantryDao) {
            this.pantryDao = pantryDao;
        }

        @Override
        protected Void doInBackground(PantryItem... pantryItems) {
            pantryDao.deleteAll();
            return null;
        }
    }

}
