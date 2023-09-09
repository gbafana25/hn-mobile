package com.example.hn_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemHolder> {
    private Context context;
    private ArrayList<NewsItem> items;
    public ArrayList<NewsItem> storage_array = new ArrayList<NewsItem>();

    public NewsItemAdapter(Context con, ArrayList<NewsItem> it) {
        this.context = con;
        this.items = it;
    }

    @Override
    public NewsItemHolder onCreateViewHolder(ViewGroup par, int view_type) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item, par, false);
        return new NewsItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemHolder holder, int position) {
        NewsItem n = items.get(position);
        holder.setDetails(n);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class NewsItemHolder extends RecyclerView.ViewHolder {
        private TextView item_title;
        private TextView item_content;
        private TextView item_author;
        private Button see_more;
        private Chip read_later;
        public NewsItemHolder(View view) {
            super(view);
            item_title = view.findViewById(R.id.newsitem_title);
            item_content = view.findViewById(R.id.newsitem_content);
            item_author = view.findViewById(R.id.newsitem_author);
            see_more = view.findViewById(R.id.see_more_btn);
            read_later = view.findViewById(R.id.read_later_chip);

        }

        @SuppressLint("SetTextI18n")
        public void setDetails(NewsItem n) {
            item_title.setText(n.getTitle());
            item_content.setText(n.getContent());
            item_author.setText("by " +n.getAuthor());

            see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Objects.equals(n.getContentType(), "url")) {
                        Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(n.getContent()));
                        context.startActivity(urlintent);

                    } else if(Objects.equals(n.getContentType(), "string")) {
                        Intent readerIntent = new Intent(context, FullItemView.class);
                        n.loadComments();
                        readerIntent.putExtra("title", n.getTitle());
                        readerIntent.putExtra("body", n.getFullContent());
                        readerIntent.putExtra("comments", n.getCommentArray());
                        context.startActivity(readerIntent);
                    }
                }
            });

            read_later.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    // create storage file w/ json array: saved_items: []

                    //System.out.println(n.getTitle()+" has been toggled");
                    if(read_later.isChecked()) {
                        storage_array.add(n);
                    } else {
                        for(int i = 0; i < storage_array.size(); i++) {
                            if(Objects.equals(n.getTitle(), storage_array.get(i).getTitle())) {
                                storage_array.remove(i);
                                break;
                            }
                        }
                    }

                }
            });


        }
    }
}
