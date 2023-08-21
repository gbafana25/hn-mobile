package com.example.hn_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView test = findViewById(R.id.test);
        OkHttpClient client = new OkHttpClient();

        hnapi api  = new hnapi();
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
                    for(int i = 0; i < 10; i++) {
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
                                    NewsItem itemobj = new NewsItem(info.getString("title"), info.getInt("score"), c, info.getString("author"), info.getString("type"), info.getInt("time"));
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