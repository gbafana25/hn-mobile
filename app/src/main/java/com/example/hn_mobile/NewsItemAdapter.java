package com.example.hn_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        public NewsItemHolder(View view) {
            super(view);
            item_title = view.findViewById(R.id.newsitem_title);
            item_content = view.findViewById(R.id.newsitem_content);
            item_author = view.findViewById(R.id.newsitem_author);

        }

        @SuppressLint("SetTextI18n")
        public void setDetails(NewsItem n) {
            item_title.setText(n.getTitle());
            item_content.setText(n.getContent());
            item_author.setText("by " +n.getAuthor());
        }
    }
}
