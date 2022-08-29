package com.example.ledpilotwatcher;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class WatchService extends Service {

    Context ctx =this;
    //Activity activity = (Activity) ctx;
    Timer time = new Timer();
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    final int delay = 1000; // 1000 milliseconds == 1 second

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        this.time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                WatchService.this.startNewActivity(WatchService.this.ctx, "eu.ledpilot.player");
            }
        }, 0, 30000);//put here time 1000 milliseconds=1 second



        return START_STICKY;
        //return super.onStartCommand( intent,  flags, startId);

    }

    @Override
    public void onDestroy(){
        Log.d("WATCHER","cancel");
        this.time.cancel();
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    public void startNewActivity(Context ctx, String packageName) {
        Log.d("WATCHER","Try to reload");
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            try{
                this.freeMemory();
                startActivity(launchIntent);
            } catch (Exception e) {
                Log.d("WATCHER","free memory");
                Toast.makeText(ctx,"BUG", Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("WATCHER","free memory");
            Toast.makeText(ctx,"BUG", Toast.LENGTH_LONG).show();
        }
    }
    public void freeMemory(){
        Log.d("WATCHER","free memory");
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }
}
