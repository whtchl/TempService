package callback.jdjz.com.tempservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public static final String TAG = "MyService";

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed: thread id:"+Thread.currentThread().getId());

        /*Notification notification = new Notification(R.drawable.ic_launcher,
                "有通知到来", System.currentTimeMillis());*/
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        /*notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",
                pendingIntent);*/

        Notification notification = new Notification.Builder(getApplicationContext())
                .setAutoCancel(true)
                .setContentTitle("title")
                .setContentText("describe")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .build();


        startForeground(1, notification);
        Log.d(TAG, "onCreate() executed");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开始执行后台任务
                Log.d(TAG,"onStartCommand thread id:   "+Thread.currentThread().getId());
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {

       /* public void startDownload() {
            Log.d("TAG", "startDownload() executed");
            // 执行具体的下载任务
        }*/
       public void startDownload() {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   Log.d(TAG,"Binder thread id:"+Thread.currentThread().getId());
                   // 执行具体的下载任务
               }
           }).start();
       }

    }
}
