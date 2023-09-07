package com.example.hn_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class FullItemView extends AppCompatActivity {
    private String body;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_item_view);
        Intent in = getIntent();
        body = in.getStringExtra("body");
        title = in.getStringExtra("title");
        TextView btext = findViewById(R.id.body_text);
        TextView ttext = findViewById(R.id.title_text);
        btext.setText(body);
        ttext.setText(title);
        ttext.setMovementMethod(new ScrollingMovementMethod());
    }
}