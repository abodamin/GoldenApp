package com.abdullah.goldenapp.network;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.abdullah.goldenapp.R;
import com.abdullah.goldenapp.models.Globals;
import com.abdullah.goldenapp.models.Root;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Random;

import static com.abdullah.goldenapp.models.Globals.CHANNEL_ID;
import static com.abdullah.goldenapp.models.Globals.notificationId;

public class JobTask extends AsyncTask<JobParameters, Void, JobParameters> {

    private final JobService jobService;
    private Context context;

    public JobTask(JobService jobService, Context context) {
        this.jobService = jobService;
        this.context = context;
    }

    @Override
    protected JobParameters doInBackground(JobParameters... jobParameters) {
        WsManager manager = new WsManager(this.jobService.getApplicationContext());// how to get context inside doInBackground.
        URLS urls = new URLS();

        manager.getAsJson(new JSONObject(), URLS.KEY, "", urls.goldPriceURL(), new WSResponse() {
            @Override
            public void onSuccess(String responce) {
                Root response = new Gson().fromJson(responce, Root.class);
                response.getOpenPrice();
                Log.i("__running in doInBG","____successful response: "+responce);
                //TODO: show notification if price is met to what user wants.
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_down_red)
                        .setContentTitle("textTitle")
                        .setContentText("textContent")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                Log.i("__notification",notificationManager.toString());

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(notificationId + new Random().nextInt(), builder.build());
            }

            @Override
            public void onError(String error) {
                Log.e("error in Background", "____error in background thread: "+error);
            }
        });

        return jobParameters[0];
    }

    @Override
    protected void onPostExecute(JobParameters jobParameters) {
        jobService.jobFinished(jobParameters ,false);
    }
}
