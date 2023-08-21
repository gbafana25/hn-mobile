package com.example.hn_mobile;

import android.widget.TextView;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class hnapi {
    private String base_url = "https://hacker-news.firebaseio.com/v0/";

    public Request getTopStories() {
        String full_url = base_url+"topstories.json?print=pretty";

        //OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url(full_url)
                .get()
                .build();
        return req;

    }

    public Request getItem(int item) {
        String full_url = base_url+"item/"+item+".json?print=pretty";
         Request req = new Request.Builder()
                 .url(full_url)
                 .get()
                 .build();
         return req;
    }
}
