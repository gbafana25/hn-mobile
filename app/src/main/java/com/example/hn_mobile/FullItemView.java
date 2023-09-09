package com.example.hn_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class FullItemView extends AppCompatActivity {
    private String body;
    private String title;
    private ArrayList<String> comment_array = new ArrayList<>();
    private String comment_section;
    private ScrollView comscroll;
    private ScrollView storyscroll;
    private Button show_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_item_view);
        Intent in = getIntent();
        body = in.getStringExtra("body");
        title = in.getStringExtra("title");
        comment_array.addAll(in.getStringArrayListExtra("comments"));
        //System.out.println(comment_array.size());
        for(int i = 0; i < comment_array.size(); i++) {
            //comment_section = String.valueOf(comment_array.get(i) + "\n");
            comment_section += comment_array.get(i)+"\n";
        }
        TextView btext = findViewById(R.id.body_text);
        TextView ttext = findViewById(R.id.title_text);
        TextView comtext = findViewById(R.id.comment_box);
        comscroll = findViewById(R.id.comments_scrollview);
        storyscroll = findViewById(R.id.story_scroll);
        show_btn = findViewById(R.id.comments_btn);
        btext.setText(body);
        ttext.setText(title);
        comtext.setText(comment_section);
        ttext.setMovementMethod(new ScrollingMovementMethod());
    }

    public void showComments(View view) {
        comscroll.setVisibility(View.VISIBLE);
        storyscroll.setVisibility(View.GONE);
        show_btn.setVisibility(View.GONE);
    }

}