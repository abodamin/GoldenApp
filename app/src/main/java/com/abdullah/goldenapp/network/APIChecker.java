package com.abdullah.goldenapp.network;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.abdullah.goldenapp.R;
import com.abdullah.goldenapp.activities.MainActivity;
import com.abdullah.goldenapp.models.NotificationUtil;
import com.abdullah.goldenapp.models.Root;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Random;

import static com.abdullah.goldenapp.models.Globals.CHANNEL_ID;
import static com.abdullah.goldenapp.models.Globals.notificationId;


public class APIChecker extends JobService {

    private final String TAG = "__APIChecker";

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //tells the OS we are handling the thread.
//        new JobTask(this, getBaseContext()).execute(jobParameters);

        Log.i(TAG, "__started new job");
//        NotificationUtil.createNotificationChannel(getApplicationContext());
        completeJob(jobParameters);

        Log.i(TAG, "__should see notification now");

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i("__running in onStop", "__stopping task");
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
                    Log.i(TAG, "__startJob, response: " + responce);
                    //TODO: show notification if price is met to what user wants.
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_down_red)
                            .setContentTitle("textTitle")
                            .setContentText("textContent")

                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());
                    Log.i("__notification", notificationManager.toString());

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(notificationId * new Random().nextInt(), builder.build());
                }

                @Override
                public void onError(String error) {
                    Log.e(TAG, "____error in background thread: " + error);
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
