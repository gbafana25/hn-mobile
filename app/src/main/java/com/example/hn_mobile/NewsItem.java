package com.example.hn_mobile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsItem {
    private String title;
    private int score;
    private String content;
    private String content_full;
    private String author;
    // use type to determine if the text or url fields are available

    private String type;
    private int time;
    private String content_type; // url or string
    private JSONArray comment_ids;
    private ArrayList<JSONObject> comments;

    public NewsItem(String title, int score, String content, String author, String type, int time, String content_type, String content_full, JSONArray comment_ids) {
        this.title = title;
        this.score = score;
        this.content = content;
        this.author = author;
        this.type = type;
        this.time = time;
        this.content_type = content_type;
        this.content_full = content_full;
        try {
            if(comment_ids != null) {
                this.comment_ids = new JSONArray(comment_ids);
            }
        } catch (JSONException e) {
            //throw new RuntimeException(e);
            // ignore
        }

    }

    public String getTitle() {
        return this.title;
    }
    public String getContent() { return this.content; }
    public String getAuthor() { return this.author; }
    public String getType() { return this.type; }
    public String getContentType() { return this.content_type; }
    public String getFullContent() { return this.content_full; }
    public void loadComments() {
        if(this.comment_ids != null) {
            hnapi api = new hnapi();
            OkHttpClient client = new OkHttpClient();
            for(int i = 0; i < this.comment_ids.length(); i++) {
                try {
                    Request item = api.getItem(this.comment_ids.getInt(i));
                    reqCallback itemCall = new reqCallback();
                    client.newCall(item).enqueue(itemCall);

                    Response resp = itemCall.get();
                    assert resp.body() != null;
                    JSONObject com = new JSONObject(resp.body().string());
                    System.out.println(resp.body().string());
                    this.comments.add(com);
                } catch (JSONException | ExecutionException | InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
