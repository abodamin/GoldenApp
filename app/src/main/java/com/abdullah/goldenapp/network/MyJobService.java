package com.abdullah.goldenapp.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.abdullah.goldenapp.R;
import com.abdullah.goldenapp.models.Root;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Random;

import static com.abdullah.goldenapp.models.Globals.CHANNEL_ID;
import static com.abdullah.goldenapp.models.Globals.notificationId;

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(@NonNull final JobParameters job) {

        //Offloading work to a new thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                completeJob(job);
            }
        }).start();

        return false;//is there any remaining work?
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return false;
    }

    public void completeJob(final JobParameters parameters) {
        try {
            WsManager manager = new WsManager(getApplicationContext());// how to get context inside doInBackground.
            URLS urls = new URLS();

            manager.getAsJson(new JSONObject(), URLS.KEY, "", urls.goldPriceURL(), new WSResponse() {
                @Override
                public void onSuccess(String responce) {
                    Root response = new Gson().fromJson(responce, Root.class);
                    response.getOpenPrice();
                    Log.i("__running in doInBG","____successful response: "+responce);
                    //TODO: show notification if price is met to what user wants.
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_down_red)
                            .setContentTitle("textTitle")
                            .setContentText("textContent")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                    Log.i("__notification",notificationManager.toString());

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(notificationId + new Random().nextInt(), builder.build());
                }

                @Override
                public void onError(String error) {
                    Log.e("error in Background", "____error in background thread: "+error);
                }
            });
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //Tell the framework that the job has completed and does not needs to be reschedule
            jobFinished(parameters, false);
        }
    }
}
