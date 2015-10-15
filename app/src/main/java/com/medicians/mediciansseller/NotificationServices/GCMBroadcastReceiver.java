package com.medicians.mediciansseller.NotificationServices;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.R;

/**
 * Created by dilpreet on 15/10/15.
 */
public class GCMBroadcastReceiver extends GcmReceiver {
    private static final String TAG = "GCM Receiver";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private Context ctx;
    public static  int notify;
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "Message received");

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        ctx = context;
        String messageType = gcm.getMessageType(intent);
        if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
            sendNotification("Send error: " + intent.getExtras().toString());
        }
        else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                .equals(messageType)) {
            sendNotification("Deleted messages on server: "
                    + intent.getExtras().toString());
        }
        else {
            sendNotification("" + intent.getExtras().getString("message"));
        }
        setResultCode(Activity.RESULT_OK);
    }

    private void sendNotification(String msg){
        mNotificationManager = (NotificationManager) ctx
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                new Intent(ctx, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                ctx).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Medicians")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        notify=1;
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }




}
