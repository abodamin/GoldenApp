package com.abdullah.goldenapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Constraints;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.abdullah.goldenapp.R;
import com.abdullah.goldenapp.adapters.CardListAdapter;
import com.abdullah.goldenapp.models.CardElementData;
import com.abdullah.goldenapp.models.Globals;
import com.abdullah.goldenapp.models.NotificationUtil;
import com.abdullah.goldenapp.models.Root;
import com.abdullah.goldenapp.network.APIChecker;
import com.abdullah.goldenapp.network.MyJobService;
import com.abdullah.goldenapp.network.URLS;
import com.abdullah.goldenapp.network.WSResponse;
import com.abdullah.goldenapp.network.WsManager;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.abdullah.goldenapp.models.Globals.CHANNEL_ID;
import static com.abdullah.goldenapp.models.Globals.JOB_ID;

public class MainActivity extends AppCompatActivity {


    private List<CardElementData> dataList;
    private RecyclerView mainList;
    private ProgressBar loader;
    private CardListAdapter cardListAdapter;
//    private JobInfo jobInfo;
    private String TAG = "__MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        firebaseDisatcherScheduler();
        mJobScheduler();


        loader = findViewById(R.id.loader);
        dataList = new ArrayList<CardElementData>();
        mainList = findViewById(R.id.mainList);
        mainList.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        //then get silverPrice.
        getGoldPrice();



//        createNotificationChannel();
    }

    private void getSilverPrice() {
        WsManager manager = new WsManager(getBaseContext());
        URLS urls = new URLS();
        manager.getAsJson(
                new JSONObject(),
                URLS.KEY,
                "",
                urls.silverPriceURL(),
                new WSResponse() {
                    @Override
                    public void onSuccess(String responce) {
                        Log.i(TAG, "___ getSilverPrice: " + responce);
                        Root response = new Gson().fromJson(responce, Root.class);
                        //
                        CardElementData silverElementData = new CardElementData();

                        double pricePerGram = calculateGoldPriceLocally(response.getPrice());

                        //TODO: calculate the actual price for Silver.
                        silverElementData.setTitle("سعر الفضة");
                        silverElementData.setPrice(pricePerGram);
                        silverElementData.setChangeRate(response.getChp());
                        silverElementData.setChangePrice(response.getCh());
                        silverElementData.setK18(calculateK18(pricePerGram) + "");
                        silverElementData.setK21(calculateK21(pricePerGram) + "");
                        silverElementData.setK24(pricePerGram + "");
                        dataList.add(silverElementData);

                        loader.setVisibility(View.GONE);
                        cardListAdapter = new CardListAdapter(getBaseContext(), dataList);
                        mainList.setAdapter(cardListAdapter);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "__error getSilverPrice__: " + error);
                    }
                });
    }

    private void getGoldPrice() {
        WsManager manager = new WsManager(getBaseContext());
        URLS urls = new URLS();
        manager.getAsJson(
                new JSONObject(),
                URLS.KEY,
                "",
                urls.goldPriceURL(),
                new WSResponse() {
                    @Override
                    public void onSuccess(String responce) {
                        Log.i(TAG, "___getGoldPrice : " + responce);
                        Root response = new Gson().fromJson(responce, Root.class);
                        //
                        CardElementData goldElementData = new CardElementData();
                        CardElementData silverElementData = new CardElementData();

                        double pricePerGram = calculateGoldPriceLocally(response.getPrice());

                        goldElementData.setTitle("سعر الذهب");
                        goldElementData.setPrice(pricePerGram);
                        goldElementData.setChangeRate(response.getChp());
                        goldElementData.setChangePrice(response.getCh());
                        goldElementData.setK18(calculateK18(pricePerGram) + "");
                        goldElementData.setK21(calculateK21(pricePerGram) + "");
                        goldElementData.setK24(pricePerGram + "");
                        dataList.add(goldElementData);
                        getSilverPrice();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(getAttributionTag(), "__error getGoldPrice__: " + error);
                    }
                });
    }

    private void mJobScheduler() {
        JobInfo jobInfo;
        final JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(JOB_ID*new Random().nextInt(), new ComponentName(this, APIChecker.class))
                    .setPeriodic(15 * 60 * 1000, 7 * 60 * 1000)
                    .build();
        } else {
            jobInfo = new JobInfo.Builder(JOB_ID*new Random().nextInt(), new ComponentName(this, APIChecker.class))
                    .setPeriodic(60 * 1000)
                    .build();
        }
        jobScheduler.schedule(jobInfo);
    }

    private void firebaseDisatcherScheduler() {
        // Create a new dispatcher using the Google Play driver.
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getBaseContext()));

        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class) // the JobService that will be called
                .setTag(Globals.jobSchedulerTag)// uniquely identifies the job
                .setTrigger(Trigger.executionWindow(1, 60))
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setRecurring(true)
                .build();

        dispatcher.mustSchedule(myJob);
    }

    //Returns price in Riyals.
    public double calculateGoldPriceLocally(double globalPrice) {
        double temp = globalPrice + 8;
        double gk24 = (temp * 120.56) / 1000;
        double rounded = (double) Math.round(gk24 * 100) / 100;
        return rounded;
//        double goldenGram = convertGoldOZtoGram(globalPrice);
//        double riyalPerGram = convertGramToSAR(goldenGram);
//        return riyalPerGram;
    }

    public double calculateK21(double gk24price) {
        double temp = gk24price / 24;
        double gk21 = temp * 21;
        double rounded = (double) Math.round(gk21 * 100) / 100;
        return rounded;
    }

    public double calculateK18(double gk24price) {
        double temp = gk24price / 24;
        double gk18 = temp * 18;
        double rounded = (double) Math.round(gk18 * 100) / 100;
        return rounded;
    }

    //Returns price in Riyals.
    private double convertGramToSAR(double goldenGramPerDollar) {
        double result = goldenGramPerDollar * 3.75;
        return result;
    }

    //Returns price of gram of gold.
    public double convertGoldOZtoGram(double globalPrice) {
        double result = globalPrice * 31.1;
        return result;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}