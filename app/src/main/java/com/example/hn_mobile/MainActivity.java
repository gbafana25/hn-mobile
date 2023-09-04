package com.example.hn_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ArrayList<NewsItem> items = new ArrayList<>();
    private ArrayList<NewsItem> top_items = new ArrayList<>();
    private ArrayList<NewsItem> show_items = new ArrayList<>();
    private NewsItemAdapter adapter = new NewsItemAdapter(this, items);
    private int num_items_show = 30;
    private int max_text_len = 300;
    hnapi api  = new hnapi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView item_list = findViewById(R.id.news_item_recycler);
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setOnNavigationItemSelectedListener(this);
        nav.setSelectedItemId(R.id.home_btn);


        item_list.setLayoutManager(new LinearLayoutManager(this));

        //adapter = new NewsItemAdapter(this, items);
        item_list.setAdapter(adapter);

        OkHttpClient client = new OkHttpClient();

        // load both sections when the app starts

        Request top = api.getTopStories();
        Request show = api.getShowStores();

        reqCallback top_call = new reqCallback();
        reqCallback show_call = new reqCallback();
        client.newCall(top).enqueue(top_call);
        client.newCall(show).enqueue(show_call);

        try {
            Response resp = top_call.get();
            assert resp.body() != null;
            OkHttpClient loop = new OkHttpClient();

            JSONArray obj = new JSONArray(resp.body().string());
            //System.out.println(obj);
            for(int i = 0; i < 30; i++) {
                Request item = api.getItem(obj.getInt(i));
                reqCallback item_call = new reqCallback();
                loop.newCall(item).enqueue(item_call);
                Response item_resp = item_call.get();
                JSONObject info = new JSONObject(item_resp.body().string());
                String c = "";
                if(info.has("text")) {
                    c = info.getString("text");
                    //System.out.println(c.length());
                    if(c.length() > max_text_len) {
                        c = info.getString("text").substring(0, max_text_len)+"...";

                    }
                } else if(info.has("url")) {
                    c = info.getString("url");
                }
                NewsItem itemobj = new NewsItem(info.getString("title"), info.getInt("score"), c, info.getString("by"), info.getString("type"), info.getInt("time"));
                top_items.add(itemobj);
                items.add(itemobj);
                //System.out.println(top_items.size());
                adapter.notifyDataSetChanged();


            }
        } catch (ExecutionException | InterruptedException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }

        try {
            Response resp = show_call.get();
            assert resp.body() != null;
            OkHttpClient loop = new OkHttpClient();

            JSONArray obj = new JSONArray(resp.body().string());
            //System.out.println(obj);
            for(int i = 0; i < 30; i++) {
                Request item = api.getItem(obj.getInt(i));
                reqCallback item_call = new reqCallback();
                loop.newCall(item).enqueue(item_call);
                Response item_resp = item_call.get();
                JSONObject info = new JSONObject(item_resp.body().string());
                String c = "";
                if(info.has("text")) {
                    c = info.getString("text");
                    //System.out.println(c.length());
                    if(c.length() > max_text_len) {
                        c = info.getString("text").substring(0, max_text_len)+"...";

                    }
                } else if(info.has("url")) {
                    c = info.getString("url");
                }
                NewsItem itemobj = new NewsItem(info.getString("title"), info.getInt("score"), c, info.getString("by"), info.getString("type"), info.getInt("time"));
                show_items.add(itemobj);


            }
        } catch (ExecutionException | InterruptedException | IOException | JSONException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int selected = menuItem.getItemId();
        //items.clear();
        if(selected == R.id.home_btn) {
            items.clear();
            items.addAll(top_items);
            adapter.notifyDataSetChanged();
            return true;
        } else if (selected == R.id.show_btn){
            items.clear();
            items.addAll(show_items);
            adapter.notifyDataSetChanged();
            return true;
        }
        return false;
    }


}