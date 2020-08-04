package com.example.artistwalesapplication;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class JsonUtils {

    public static String getJsonStringFromUrl(String ART_URL, String ART_HEADER)
    {
        URL url = null;
        String json = "";
        HttpURLConnection urlConnection;

        try {
            url = new URL(ART_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("secret-key", ART_HEADER);

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String str = "";
            while (null != (str = br.readLine())) {
                json+=str;
            }
            return json;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJsonFromUrl(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

