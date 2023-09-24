package com.example.hn_mobile;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
    private ArrayList<CommentItem> comment_array = new ArrayList<>();
    private ScrollView storyscroll;
    private Button show_btn;
    private CommentItemAdapter comment_adapter;
    private RecyclerView comment_view;


    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_item_view);
        Intent in = getIntent();
        body = in.getStringExtra("body");
        title = in.getStringExtra("title");
        comment_array = in.getParcelableArrayListExtra("comments");
        //System.out.println(comment_array.size());

        /*
        for(int i = 0; i < comment_array.size(); i++) {
            System.out.println(comment_array.get(i).getContent());

        }

         */


        TextView btext = findViewById(R.id.body_text);
        TextView ttext = findViewById(R.id.title_text);
        //TextView comtext = findViewById(R.id.comment_box);
        //comscroll = findViewById(R.id.comments_scrollview);
        storyscroll = findViewById(R.id.story_scroll);
        show_btn = findViewById(R.id.comments_btn);

        if(comment_array.size() == 0) {
            show_btn.setEnabled(false);
        }

        /*
        if(comment_array.size() == 0) {
            //show_btn.setClickable(false);
            show_btn.setEnabled(false);
            show_btn.setText("No comments");
        }

         */

        btext.setText(body);
        ttext.setText(title);
        //comtext.setText(comment_section);
        ttext.setMovementMethod(new ScrollingMovementMethod());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void showComments(View view) {
        //comment_adapter.notifyDataSetChanged();
        /*
        comment_view.setVisibility(view.VISIBLE);
        storyscroll.setVisibility(view.GONE);
        show_btn.setVisibility(view.GONE);
         */
        Intent c = new Intent(this, CommentView.class);
        c.putParcelableArrayListExtra("comments", comment_array);
        startActivity(c);
    }

}