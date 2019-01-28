package com.gebeya.framework.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.gebeya.smartcontract.MainActivity;
import com.gebeya.smartcontract.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import static com.gebeya.framework.utils.Constants.PREFS_NAME;

public class FireMsgService extends FirebaseMessagingService {

    SharedPreferences sharedpreferences;

    private static final String TAG = "FireMsgService";
    public static final String DEVICE_ID = "DEVICE_TOKEN_ID";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToSharedPreference(token);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification()
                  .getBody());
        }
        sendNotification((Objects.requireNonNull(remoteMessage.getNotification())).getBody());
    }

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {

        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
              .setService(MyJobService.class)
              .setTag("my-job-tag")
              .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToSharedPreference(String token) {
        //sharedpreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        // Save changes to save preference
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(DEVICE_ID, token);
        editor.apply();
        //Toast()
        String restoredText = sharedpreferences.getString(DEVICE_ID, "");
        Log.d(TAG, restoredText);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
              PendingIntent.FLAG_ONE_SHOT);


        String channelId = getString(R.string.default_notification_channel_id);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
              new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
              (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                  "Channel human readable title",
                  NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
