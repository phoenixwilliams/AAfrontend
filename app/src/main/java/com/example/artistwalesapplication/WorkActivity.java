package com.example.artistwalesapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WorkActivity extends AppCompatActivity
{

    private String jsonString;
    private ProgressBar progressBar;

    private Bitmap[] workImages;
    private String[] imageLabels;
    private String[] workPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_activity);
        this.getSupportActionBar().hide();

        Intent intentThatStartedThisActivity = getIntent();

        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT))
        {
            jsonString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            progressBar = (ProgressBar)findViewById(R.id.retrieving_work_progress_bar);
            WorkUI workUI = new WorkUI(this);
            Thread workUIThread = new Thread(workUI);
            workUIThread.start();


        }

    }

    class RecyclerUI implements Runnable
    {
        private WorkRecyclerAdapter recyclerAdapterUI;
        private RecyclerView recyclerView;

        public RecyclerUI(WorkRecyclerAdapter recyclerAdapter, RecyclerView recyclerView)
        {
            this.recyclerAdapterUI = recyclerAdapter;
            this.recyclerView = recyclerView;
        }

        @Override
        public void run() {
            recyclerView.setAdapter(recyclerAdapterUI);
        }
    }

    public class WorkUI implements Runnable
    {
        private Thread retrieveWorkThread;
        private RetrieveWork retrieveWork;
        private Context context;

        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private WorkRecyclerAdapter workRecyclerAdapter;

        public WorkUI(Context context)
        {
            this.context = context;
        }
        @Override
        public void run() {
            retrieveWork = new RetrieveWork();
            retrieveWorkThread = new Thread(retrieveWork);
            retrieveWorkThread.start();

            try {
                retrieveWorkThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView = (RecyclerView) findViewById(R.id.workRecyclerView);
                    layoutManager = new LinearLayoutManager(context);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    workRecyclerAdapter = new WorkRecyclerAdapter(workImages, imageLabels, workPrices);
                    recyclerView.setAdapter(workRecyclerAdapter);
                }
            });

        }
    }

    public class RetrieveWork implements Runnable
    {

        @Override
        public void run() {
            workImages = WorkJsonHandler.getWorkImages(jsonString);
            imageLabels = WorkJsonHandler.getWorkLabels(jsonString);
            workPrices = WorkJsonHandler.getWorkPrices(jsonString);
        }
    }

}
