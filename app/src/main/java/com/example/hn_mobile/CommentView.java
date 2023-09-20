package com.example.hn_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;

public class CommentView extends AppCompatActivity {
    private RecyclerView comment_view;
    private CommentItemAdapter adapter;
    private ArrayList<CommentItem> item_comments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);
        //TextView comment_box = findViewById(R.id.comment_only_box);
        Intent com = getIntent();
        item_comments = com.getParcelableArrayListExtra("comments");
        //StringBuilder comment_string = new StringBuilder();
        comment_view = findViewById(R.id.comments);
        comment_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentItemAdapter(this, item_comments);
        comment_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        /*
        for(int i = 0; i < item_comments.size(); i++) {
            comment_string.append(Html.fromHtml(item_comments.get(i)).toString()).append("\n\n");
        }

         */
        //comment_box.setText(comment_string);

    }
}