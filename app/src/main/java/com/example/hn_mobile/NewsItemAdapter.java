package com.example.hn_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemHolder> {
    private Context context;
    private ArrayList<NewsItem> items;

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
        public NewsItemHolder(View view) {
            super(view);
            item_title = view.findViewById(R.id.newsitem_title);
            item_content = view.findViewById(R.id.newsitem_content);
            item_author = view.findViewById(R.id.newsitem_author);
            see_more = view.findViewById(R.id.see_more_btn);

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
                        readerIntent.putExtra("title", n.getTitle());
                        readerIntent.putExtra("body", n.getFullContent());
                        context.startActivity(readerIntent);
                    }
                }
            });


        }
    }
}
