package com.example.hn_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SavedView extends AppCompatActivity {
    private ArrayList<NewsItem> items = new ArrayList<>();
    private SavedViewAdapter adapter = new SavedViewAdapter(this, items);
    private RecyclerView saved_items;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_view);
        saved_items = findViewById(R.id.saved_items);
        saved_items.setLayoutManager(new LinearLayoutManager(this));
        saved_items.setAdapter(adapter);
        //StringBuilder output_str = new StringBuilder();
        try {
            FileInputStream f = this.openFileInput("storage.json");
            InputStreamReader input = new InputStreamReader(f, StandardCharsets.UTF_8);
            try(BufferedReader reader = new BufferedReader(input)) {
                String l = reader.readLine();
                while(l != null) {
                    //output_str.append(l).append("\n");
                    //System.out.println(l);
                    JSONObject info = new JSONObject(l);
                    if(info.has("comment_ids") && !info.getString("comment_ids").equals("[]")) {
                        String str_array = info.getString("comment_ids").substring(1, info.getString("comment_ids").length()-1);
                        System.out.println(str_array);
                        JSONArray comment_array = new JSONArray(str_array.split(","));
                        items.add(new NewsItem(info.getString("title"), 0, info.getString("content"), info.getString("author"), info.getString("type"), 0, info.getString("content_type"), info.getString("content_full"), comment_array));

                    } else {
                        items.add(new NewsItem(info.getString("title"), 0, info.getString("content"), info.getString("author"), info.getString("type"), 0, info.getString("content_type"), info.getString("content_full"), null));
                    }


                    //System.out.println(item.getString("title"));
                    l = reader.readLine();

                }
                adapter.notifyDataSetChanged();

                //System.out.println(items.size());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}