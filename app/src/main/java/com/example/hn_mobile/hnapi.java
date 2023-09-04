package com.example.hn_mobile;

import android.widget.TextView;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class hnapi {
    private String base_url = "https://hacker-news.firebaseio.com/v0/";
    private int num_items_show = 30;


    public Request getTopStories() {
        String full_url = base_url+"topstories.json?print=pretty";

        //OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url(full_url)
                .get()
                .build();
        return req;

    }

    public Request getShowStores() {
        String full_url = base_url+"showstories.json?print=pretty";

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

    public void runRequest(Request req, ArrayList<NewsItem> items) {
        OkHttpClient client = new OkHttpClient();
        hnapi api  = new hnapi();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    JSONArray obj = new JSONArray(response.body().string());
                    OkHttpClient loop = new OkHttpClient();
                    for(int i = 0; i < num_items_show; i++) {
                        Request it = api.getItem(obj.getInt(i));
                        loop.newCall(it).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                try {
                                    JSONObject info = new JSONObject(response.body().string());
                                    String c = "";
                                    if(info.has("text")) {
                                        c = info.getString("text");
                                    } else if(info.has("url")) {
                                        c = info.getString("url");
                                    }
                                    NewsItem itemobj = new NewsItem(info.getString("title"), info.getInt("score"), c, info.getString("by"), info.getString("type"), info.getInt("time"));
                                    items.add(itemobj);



                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        });
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}

class reqCallback extends CompletableFuture<Response> implements Callback {
    public void onResponse(Call call, Response response) {
        super.complete(response);
    }
    public void onFailure(Call call, IOException e) {
        super.completeExceptionally(e);
    }
}

