package com.example.artistwalesapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class ContactActivity extends AppCompatActivity
{
    private ListRecyclerAdapter listRecyclerAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private String[] contact;
    private String[] contactDetails;
    private String jsonString;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.number_list_activity);
        this.getSupportActionBar().hide();
        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT))
        {
            jsonString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            progressBar = (ProgressBar)findViewById(R.id.contact_progress_bar);

            WorkUI workUI = new WorkUI(this,this);
            Thread workThread = new Thread(workUI);
            workThread.start();

        }

    }

    public void openWebPage(String url)
    {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public class WorkUI implements Runnable
    {
        private Thread retrieveWorkThread;
        private RetrieveWork retrieveWork;
        private Context context;
        private ContactActivity contactActivity;

        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private ListRecyclerAdapter listRecyclerAdapter;

        public WorkUI(Context context, ContactActivity contactActivity)
        {
            this.context = context;
            this.contactActivity = contactActivity;
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
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView = (RecyclerView) findViewById(R.id.rv_numbers);

                    layoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    listRecyclerAdapter = new ListRecyclerAdapter(contact,contactDetails,contactActivity);
                    recyclerView.setAdapter(listRecyclerAdapter);
                }
            });

        }
    }

    public class RetrieveWork implements Runnable
    {
        @Override
        public void run() {
            contact = WorkJsonHandler.getContactMethods(jsonString);
            contactDetails = WorkJsonHandler.getContactDetails(jsonString);
        }
    }

}
