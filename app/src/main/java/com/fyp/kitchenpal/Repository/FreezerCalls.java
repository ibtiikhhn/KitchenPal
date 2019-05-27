package com.fyp.kitchenpal.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.fyp.kitchenpal.Model.FreezerItem;

import java.util.List;

public class FreezerCalls {
    private FreezerDao freezerDao;
    LiveData<List<FreezerItem>> freezerItems;

    public FreezerCalls(Application application) {
        MyDatabase myDatabase = MyDatabase.getDatabase(application);
        freezerDao = myDatabase.freezerDao();
        freezerItems = freezerDao.getAllFreezerItems();
    }

    public void insert(FreezerItem freezerItem) {
        new InsertItem(freezerDao).execute(freezerItem);
    }

    public void update(FreezerItem freezerItem) {
        new UpdateItem(freezerDao).execute(freezerItem);
    }

    public void delete(FreezerItem freezerItem) {
        new DeleteItem(freezerDao).execute(freezerItem);
    }

    public void deleteAll() {
        new DeleteAll(freezerDao).execute();
    }

    public LiveData<List<FreezerItem>> getFreezerItems() {
        return freezerItems;
    }

    public static class InsertItem extends AsyncTask<FreezerItem, Void, Void> {

        FreezerDao freezerDao;

        InsertItem(FreezerDao freezerDao) {
            this.freezerDao = freezerDao;
        }

        @Override
        protected Void doInBackground(FreezerItem... freezerItems) {
            freezerDao.insert(freezerItems[0]);
            return null;
        }
    }

    public static class DeleteItem extends AsyncTask<FreezerItem, Void, Void> {

        FreezerDao freezerDao;

        DeleteItem(FreezerDao freezerDao) {
            this.freezerDao = freezerDao;
        }

        @Override
        protected Void doInBackground(FreezerItem... freezerItems) {
            freezerDao.delete(freezerItems[0]);
            return null;
        }
    }

    public static class UpdateItem extends AsyncTask<FreezerItem, Void, Void> {

        FreezerDao freezerDao;

        UpdateItem(FreezerDao freezerDao) {
            this.freezerDao = freezerDao;
        }

        @Override
        protected Void doInBackground(FreezerItem... freezerItems) {
            freezerDao.insert(freezerItems[0]);
            return null;
        }
    }

    public static class DeleteAll extends AsyncTask<FreezerItem, Void, Void> {

        FreezerDao freezerDao;

        DeleteAll(FreezerDao freezerDao) {
            this.freezerDao = freezerDao;
        }

        @Override
        protected Void doInBackground(FreezerItem... freezerItems) {
            freezerDao.insert(freezerItems[0]);
            return null;
        }
    }

}
