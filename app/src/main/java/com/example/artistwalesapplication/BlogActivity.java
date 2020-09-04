package com.example.artistwalesapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class BlogActivity extends AppCompatActivity
{
    private String jsonString;
    private ProgressBar progressBar;
    private String[] blogs;
    private String[] dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_activity);
        this.getSupportActionBar().hide();

        Intent intentThatStartedThisActivity = getIntent();
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT))
        {
            jsonString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            progressBar = (ProgressBar)findViewById(R.id.retrieving_blog_progress_bar);

            WorkUI workUI = new WorkUI(this);
            Thread workThread = new Thread(workUI);
            workThread.start();
        }
    }

    public class WorkUI implements Runnable
    {
        private Thread retrieveWorkThread;
        private RetrieveWork retrieveWork;
        private Context context;

        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private BlogRecyclerAdapter blogRecyclerAdapter;

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
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView = (RecyclerView) findViewById(R.id.blogRecyclerView);
                    layoutManager = new LinearLayoutManager(context);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(layoutManager);
                    blogRecyclerAdapter = new BlogRecyclerAdapter(blogs,dates);
                    recyclerView.setAdapter(blogRecyclerAdapter);
                }
            });

        }
    }

    public class RetrieveWork implements Runnable
    {
        @Override
        public void run() {
            blogs = WorkJsonHandler.getBlogs(jsonString);
            dates = WorkJsonHandler.getBlogDates(jsonString);
        }
    }
}
