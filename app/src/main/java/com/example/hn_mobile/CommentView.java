package com.example.hn_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);
        TextView comment_box = findViewById(R.id.comment_only_box);
        Intent com = getIntent();
        ArrayList<String> item_comments = com.getStringArrayListExtra("comments");
        StringBuilder comment_string = new StringBuilder();
        for(int i = 0; i < item_comments.size(); i++) {
            comment_string.append(Html.fromHtml(item_comments.get(i)).toString()).append("\n\n");
        }
        comment_box.setText(comment_string);

    }
}