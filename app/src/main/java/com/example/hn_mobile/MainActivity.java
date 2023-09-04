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
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements BottomNavigationView
        .OnNavigationItemSelectedListener {
    private ArrayList<NewsItem> items = new ArrayList<>();
    private NewsItemAdapter adapter;
    private int num_items_show = 30;
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

        adapter = new NewsItemAdapter(this, items);
        item_list.setAdapter(adapter);

        OkHttpClient client = new OkHttpClient();


        Request top = api.getTopStories();

        client.newCall(top).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //System.out.println(response.body().string());

                try {
                    assert response.body() != null;
                    OkHttpClient loop = new OkHttpClient();
                    JSONArray obj = new JSONArray(response.body().string());
                    //System.out.println(obj);
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
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                        }
                                    });

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int selected = menuItem.getItemId();
        items.clear();
        if(selected == R.id.home_btn) {
            //System.out.println("home");
            Request top = api.getTopStories();
            OkHttpClient client = new OkHttpClient();
            client.newCall(top).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        assert response.body() != null;
                        OkHttpClient loop = new OkHttpClient();
                        JSONArray obj = new JSONArray(response.body().string());
                        //System.out.println(obj);
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
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });

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
            return true;
        } else if (selected == R.id.show_btn){
            //System.out.println("show");
            Request show = api.getShowStores();
            OkHttpClient client = new OkHttpClient();
            client.newCall(show).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        assert response.body() != null;
                        OkHttpClient loop = new OkHttpClient();
                        JSONArray obj = new JSONArray(response.body().string());
                        //System.out.println(obj);
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
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });

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
            return true;
        }
        return false;
    }
}