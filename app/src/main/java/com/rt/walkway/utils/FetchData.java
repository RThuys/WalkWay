package com.rt.walkway.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.rt.walkway.ItemListActivity;
import com.rt.walkway.classes.Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<String, Void, Path[]> {
    //
    //https://api.myjson.com/bins/q8hfg  //json example
    static JSONObject jsonObject = null;
    PrintListner mPrintListner;

   // private ItemListActivity.SimpleItemRecyclerViewAdapter adapter = new ItemListActivity.SimpleItemRecyclerViewAdapter(this);

    public FetchData() {

    }

    public FetchData(PrintListner mPrintListner) {
        this.mPrintListner = mPrintListner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Path[] doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        JSONArray data = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);
            }
            try {
                Path[] simpleString = OpenPathJsonUtils.getSimplePathStringsFromJson(buffer.toString());
                return simpleString;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Path[] result) {
        this.mPrintListner.getResult(result);
        Log.d("onPost", result[1].toString());
    }
}