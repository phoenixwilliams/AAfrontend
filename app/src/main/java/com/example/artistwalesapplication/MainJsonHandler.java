package com.example.artistwalesapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public final class MainJsonHandler {

    public static JSONObject getMainPageObj(JSONObject json)
    {
        try {
            JSONObject mainPageJson = json.getJSONObject("main_page");
            return mainPageJson;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Bitmap getMainLogo(JSONObject json)  {
        try {
            String logoSource = json.getString("main_logo_src");
            Bitmap logo = getImage(logoSource);
            return logo;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getContactLogo(JSONObject json)  {
        try {
            String logoSource = json.getString("contact_logo_src");
            Bitmap logo = getImage(logoSource);
            return logo;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getNewsLogo(JSONObject json)  {
        try {
            String logoSource = json.getString("news_logo_src");
            Bitmap logo = getImage(logoSource);
            return logo;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getWorkLogo(JSONObject json)  {
        try {
            String logoSource = json.getString("work_logo_src");
            Bitmap logo = getImage(logoSource);
            return logo;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBlogLogo(JSONObject json)  {
        try {
            String logoSource = json.getString("blog_logo_src");
            Bitmap logo = getImage(logoSource);
            return logo;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return bmp;
    }


}
