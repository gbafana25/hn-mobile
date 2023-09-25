package com.example.hn_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class SavedView extends AppCompatActivity {
    private ArrayList<NewsItem> items = new ArrayList<>();
    private SavedViewAdapter adapter = new SavedViewAdapter(this, items);
    private RecyclerView saved_items;
    private Button home;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_view);
        saved_items = findViewById(R.id.saved_items);
        saved_items.setLayoutManager(new LinearLayoutManager(this));
        saved_items.setAdapter(adapter);

        home = findViewById(R.id.saved_home);

        try {
            Intent save = getIntent();
            String status = save.getStringExtra("status");
            if(Objects.equals(status, "offline")) {
                home.setEnabled(false);
                home.setClickable(false);
            }
        } catch (Exception e) {
            System.out.println("oneline");
        }
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
                        //System.out.println(str_array);
                        JSONArray comment_array = new JSONArray(str_array.split(","));

                        NewsItem newitem = new NewsItem(info.getString("title"), 0, info.getString("content"), info.getString("author"), info.getString("type"), 0, info.getString("content_type"), info.getString("content_full"), comment_array, info.getInt("score"), info.getInt("date_posted"));

                        if(!items.contains(newitem)) {
                            items.add(newitem);
                        }


                        //items.add(newitem);

                    } else {
                        NewsItem newitem = new NewsItem(info.getString("title"), 0, info.getString("content"), info.getString("author"), info.getString("type"), 0, info.getString("content_type"), info.getString("content_full"), null, info.getInt("score"), info.getInt("date_posted"));

                        if(!items.contains(newitem)) {
                            items.add(newitem);
                        }


                        //items.add(newitem);
                    }


                    //System.out.println(item.getString("title"));
                    l = reader.readLine();

                }
                adapter.notifyDataSetChanged();

                //System.out.println(items.size());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void clearDialog(View view) {
        // alert dialog to confirm clearing of save file
        AlertDialog.Builder clear_alert = new AlertDialog.Builder(SavedView.this);
        clear_alert.setMessage("Are you sure you want to delete all saved content?");
        clear_alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearFile();
                Intent mainintent = new Intent(SavedView.this, MainActivity.class);
                startActivity(mainintent);
            }
        });
        clear_alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog clear_btn = clear_alert.create();
        clear_btn.show();

    }

    public void clearFile() {
        try(FileOutputStream fs = this.openFileOutput("storage.json", MODE_PRIVATE)) {
            fs.write("".getBytes());
            fs.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnHome(View view) {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }
}