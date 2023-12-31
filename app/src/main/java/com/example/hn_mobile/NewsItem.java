package com.example.hn_mobile;

import android.content.Context;
import android.text.Html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsItem {
    private String title;
    private String content;
    private String content_full;
    private String author;
    // use type to determine if the text or url fields are available

    private String type;
    private int time;
    private String content_type; // url or string
    private ArrayList<Integer> comment_ids;
    private ArrayList<CommentItem> comments;
    private int score;
    private boolean is_saved;
    private int date_posted;

    public NewsItem(String title, int score, String content, String author, String type, int time, String content_type, String content_full, JSONArray comment_ids, int points, int posted) {
        this.title = title;
        this.score = score;
        this.content = String.valueOf(Html.fromHtml(content));
        this.author = author;
        this.type = type;
        this.time = time;
        this.content_type = content_type;
        this.content_full = String.valueOf(Html.fromHtml(content_full));
        this.is_saved = false;
        this.score = points;
        this.date_posted = posted;
        this.comments = new ArrayList<CommentItem>();
        try {
            if(comment_ids != null) {
                //this.comment_ids = new JSONArray(comment_ids);
                this.comment_ids = new ArrayList<Integer>();
                for(int j = 0; j < comment_ids.length(); j++) {
                    this.comment_ids.add(comment_ids.getInt(j));
                }
                //System.out.println(this.comment_ids.get(0).toString());

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
    public ArrayList<CommentItem> getCommentArray() { return this.comments; }
    public ArrayList<Integer> getCommentIds() { return this.comment_ids; }
    public int getCommentArraySize() { return this.comments.size(); }
    public void setSaved(boolean b) {
        is_saved = b;
    }
    public boolean getSaved() {
        return is_saved;
    }

    public int getScore() {
        return this.score;
    }
    public int getDatePosted() { return this.date_posted; }
    public void loadComments() {
        if(this.comment_ids != null) {
            hnapi api = new hnapi();
            OkHttpClient client = new OkHttpClient();
            for(int i = 0; i < this.comment_ids.size(); i++) {
                try {
                    Request item = api.getItem(this.comment_ids.get(i));
                    reqCallback itemCall = new reqCallback();
                    client.newCall(item).enqueue(itemCall);

                    Response resp = itemCall.get();
                    assert resp.body() != null;
                    JSONObject com = new JSONObject(resp.body().string());
                    //System.out.println(resp.body().string());
                    String comstring = "";
                    if(com.has("by") && com.has("text")) {
                        //comstring = com.getString("by")+": "+com.getString("text");
                        CommentItem new_comment = new CommentItem(com.getString("by"), com.getString("text"));
                        this.comments.add(new_comment);
                    }

                } catch (JSONException | ExecutionException | InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public JSONObject toJSON() {
        JSONObject serialized = new JSONObject();
        try {
            JSONArray comarray = new JSONArray(this.comment_ids);
            serialized.put("title", this.title);
            serialized.put("author", this.author);
            serialized.put("content", this.content);
            serialized.put("content_full", this.content_full);
            serialized.put("type", this.type);
            serialized.put("content_type", this.content_type);
            serialized.put("comment_ids", comarray);
            serialized.put("score", this.score);
            serialized.put("date_posted", this.date_posted);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return serialized;
    }
}
