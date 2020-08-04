package com.example.artistwalesapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public final class WorkJsonHandler {

    public static Bitmap[] getWorkImages(String workJsonString)
    {
        Bitmap[] images;
        JSONArray workData;
        JSONObject tempObject;
        try {
            workData = new JSONArray(workJsonString);
            images = new Bitmap[workData.length()];

            for (int i=0;i<images.length;i++)
            {
                tempObject = workData.getJSONObject(i);
                images[i] = getImage(tempObject.getString("src"));
            }
            return images;

        } catch (JSONException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String[] getWorkPrices(String workJsonString)
    {
        String[] imageLabels;
        JSONArray workData;
        JSONObject tempObject;
        try {
            workData = new JSONArray(workJsonString);
            imageLabels = new String[workData.length()];

            for (int i=0;i<imageLabels.length;i++)
            {
                tempObject = workData.getJSONObject(i);
                imageLabels[i] = tempObject.getString("price_text");
            }
            return imageLabels;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String[] getWorkLabels(String workJsonString)
    {
        String[] imageLabels;
        JSONArray workData;
        JSONObject tempObject;
        try {
            workData = new JSONArray(workJsonString);
            imageLabels = new String[workData.length()];

            for (int i=0;i<imageLabels.length;i++)
            {
                tempObject = workData.getJSONObject(i);
                imageLabels[i] = tempObject.getString("text");
            }
            return imageLabels;

        } catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static Bitmap getImage(String urlString) throws IOException {
        URL url = new URL(urlString);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        return bmp;
    }

}
