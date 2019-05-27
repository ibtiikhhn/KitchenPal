package com.fyp.kitchenpal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.fyp.kitchenpal.Model.FreezerItem;
import com.fyp.kitchenpal.Model.FridgeItem;
import com.fyp.kitchenpal.Model.PantryItem;

public class NotificationManager {
    private static final String NOTIF_TITLE = "Item Expiring!";
    private static final String GROUP = "foodtracker-group";
    private static final String TAG = "NotificationManager";
    private final String CHANNEL_ID;
    private Context c;


    public NotificationManager(Context c, String channel_id) {
        this.c = c;
        this.CHANNEL_ID = channel_id;
    }

    public void scheduleNotificationForPantry(PantryItem food) {
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, ExpiryPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 1, intent, 0);

        long timeInFuture = food.getExpiryDate() - 86400000;
        Log.i(TAG, "scheduleNotificationForPantry: " + timeInFuture);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInFuture, pendingIntent);

    }

    public void scheduleNotificationForFridge(FridgeItem food) {
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, ExpiryPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 1, intent, 0);

        long timeInFuture = food.getExpiryDate() - 86400000;
        Log.i(TAG, "scheduleNotificationForPantry: " + timeInFuture);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInFuture, pendingIntent);
    }

    public void scheduleNotificationForFreezer(FreezerItem food) {
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(c, ExpiryPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(c, 1, intent, 0);

        long timeInFuture = food.getExpiryDate() - 86400000;
        Log.i(TAG, "scheduleNotificationForPantry: " + timeInFuture);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInFuture, pendingIntent);
    }

}
