package com.fyp.kitchenpal;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.quintus.labs.notification.NotificationHelper;


public class ExpiryPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context.getApplicationContext());
        notificationHelper.createNotification("Item Expired","An item in your inventory is about to expire",R.drawable.notif_icon,intent);
    }

}
