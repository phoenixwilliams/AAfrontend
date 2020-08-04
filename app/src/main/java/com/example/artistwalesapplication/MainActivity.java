package com.example.artistwalesapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    private JSONObject mainJson;
    Button blog;
    Button news;
    Button work;
    Button contact;
    ImageView mainLogo;
    TextView title;
    TextView errorMessage;
    ProgressBar progressBar;
    JSONObject jsonData;

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        this.getSupportActionBar().hide();

        //Set-up text colour of each button
        progressBar = (ProgressBar) findViewById(R.id.image_loading_progress_bar);
        progressBar.bringToFront();
        contact = (Button) findViewById(R.id.buttonContact);
        blog = (Button) findViewById(R.id.buttonBlog);
        news = (Button) findViewById(R.id.buttonNews);
        work = (Button) findViewById(R.id.buttonWork);
        title = (TextView) findViewById(R.id.title_textview);
        mainLogo = (ImageView) findViewById(R.id.mainLogo);
        errorMessage = (TextView) findViewById(R.id.Error_Message_textview);

        MainUI mainUI = new MainUI();
        Thread uiThread = new Thread(mainUI);
        uiThread.start();

        work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("work click", "work button clicked");

                Intent startChildActivityIntent = new Intent(MainActivity.this, WorkActivity.class);
                JSONArray workJsonData;
                ErrorUI errorUI = new ErrorUI("SERVER ERROR: ENSURE DEVICE IS CONNECTED TO THE INTERNET " +
                        "AND RESTART APPLICATION");;
                Thread errorThread;
                try {
                    workJsonData = jsonData.getJSONArray("work_page");
                    startChildActivityIntent.putExtra(Intent.EXTRA_TEXT, workJsonData.toString());
                    startActivity(startChildActivityIntent);
                } catch (JSONException e) {
                    errorThread = new Thread(errorUI);
                    errorThread.start();
                    e.printStackTrace();
                }

            }
        });
    }

    class MainUI implements Runnable
    {

        @Override
        public void run() {
            //Get images from Internet
            SetUpMainUrl setUpMainUrl = new SetUpMainUrl();
            Thread setUpThread = new Thread(setUpMainUrl);
            setUpThread.start();
            try {
                setUpThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.INVISIBLE);

                    SpannableString spannableString = new SpannableString("CONTACT");
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    contact.setText(spannableString);

                    spannableString = new SpannableString("BLOG");
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    blog.setText(spannableString);

                    spannableString = new SpannableString("NEWS");
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    news.setText(spannableString);

                    spannableString = new SpannableString("WORK");
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    work.setText(spannableString);

                    spannableString = new SpannableString("NEALE HOWELLS");
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED),7,8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    title.setText(spannableString);

                    news.setVisibility(View.VISIBLE);
                    work.setVisibility(View.VISIBLE);
                    blog.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.VISIBLE);

                }
            });


        }
    }

    class ErrorUI implements Runnable
    {
        private String message;

        ErrorUI(String message)
        {
            this.message=message;
        }

        @Override
        public void run() {
            final String m = this.message;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    errorMessage.setText(m);
                    errorMessage.setVisibility(View.VISIBLE);

                    mainLogo.setVisibility(View.INVISIBLE);
                    contact.setVisibility(View.INVISIBLE);
                    news.setVisibility(View.INVISIBLE);
                    work.setVisibility(View.INVISIBLE);
                    blog.setVisibility(View.INVISIBLE);
                    title.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    class SetUpMainUrl implements Runnable
    {

        private String jsonAPI = "https://api.jsonbin.io/b/5f231a99dc263a7b80b083d9";
        private String jsonHeader = "$2b$10$n/istDwcPLjoT1Sc10mNA.rmT3.ScWeXA3/PZQY/SgGz/YeBvEQGu";

        @Override
        public void run() {

            //Set up error class
            ErrorUI errorUI = new ErrorUI("SERVER ERROR: ENSURE DEVICE IS CONNECTED TO THE INTERNET " +
                    "AND RESTART APPLICATION");;
            Thread errorThread;

            //Get Json Objects from Url
            String strJson = JsonUtils.getJsonStringFromUrl(jsonAPI,jsonHeader);
            //JsonUtils.getJsonStringFromURLTest();

            if (strJson==null)
            {
                errorThread = new Thread(errorUI);
                errorThread.start();
                return;
            }
            JSONObject jsonObject = JsonUtils.getJsonFromUrl(strJson);
            jsonData = jsonObject;

            final JSONObject mainPageObj = MainJsonHandler.getMainPageObj(jsonObject);
            final Bitmap logoBit = MainJsonHandler.getMainLogo(mainPageObj);
            final Bitmap contactBit = MainJsonHandler.getContactLogo(mainPageObj);
            final Bitmap newsBit = MainJsonHandler.getNewsLogo(mainPageObj);
            final Bitmap workBit = MainJsonHandler.getWorkLogo(mainPageObj);
            final Bitmap blogBit = MainJsonHandler.getBlogLogo(mainPageObj);

            Bitmap[] logos = new Bitmap[]{logoBit,contactBit,newsBit,workBit,blogBit};
            boolean error = false;
            for (Bitmap b:logos)
            {
                if(b==null)error = true;
            }

            if(!error) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainLogo.setImageBitmap(logoBit);
                        contact.setBackground(new BitmapDrawable(getResources(), contactBit));
                        news.setBackground(new BitmapDrawable(getResources(), newsBit));
                        work.setBackground(new BitmapDrawable(getResources(), workBit));
                        blog.setBackground(new BitmapDrawable(getResources(), blogBit));
                    }
                });
            }
            else
            {
                errorThread = new Thread(errorUI);
                errorThread.start();
            }
        }
    }

}