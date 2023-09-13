package com.example.hn_mobile;

import android.text.Html;
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
    private int max_text_len = 300;


    public Request getTopStories() {
        String full_url = base_url+"topstories.json?print=pretty";

        //OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url(full_url)
                .get()
                .build();
        return req;

    }

    public Request getShowStories() {
        String full_url = base_url+"showstories.json?print=pretty";

        //OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url(full_url)
                .get()
                .build();
        return req;
    }

    public Request getAskStories() {
        String full_url = base_url+"askstories.json?print=pretty";
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

    public NewsItem parseItem(JSONObject info) throws JSONException {
        String c = "";
        String ctype = "";
        String full = "";
        if(info.has("text")) {
            c = info.getString("text");
            full = info.getString("text");
            full = Html.fromHtml(full).toString();
            ctype = "string";
            //System.out.println(c.length());
            if(c.length() > max_text_len) {
                c = info.getString("text").substring(0, max_text_len);
                c = Html.fromHtml(c).toString();
                c += "...";

            }
        } else if(info.has("url")) {
            c = info.getString("url");
            full = info.getString("url");
            ctype = "url";
        }
        NewsItem itemobj = null;
        if(info.has("kids")) {
            itemobj = new NewsItem(info.getString("title"), info.getInt("score"), c, info.getString("by"), info.getString("type"), info.getInt("time"), ctype, full, info.getJSONArray("kids"));
            //System.out.println(info.getJSONArray("kids"));
        } else {
            itemobj = new NewsItem(info.getString("title"), info.getInt("score"), c, info.getString("by"), info.getString("type"), info.getInt("time"), ctype, full, null);

        }
        return itemobj;
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

