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

public class SavedViewAdapter extends RecyclerView.Adapter<SavedViewAdapter.SavedHolder> {
    private Context context;
    private ArrayList<NewsItem> items;

    public SavedViewAdapter(Context con, ArrayList<NewsItem> it) {
        this.context = con;
        this.items = it;

    }

    @NonNull
    @Override
    public SavedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.saved_item, parent, false);
        return new SavedViewAdapter.SavedHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedHolder holder, int position) {
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

    public class SavedHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView author;
        private TextView content_short;
        public SavedHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            author = itemView.findViewById(R.id.author_text);
            content_short = itemView.findViewById(R.id.content_short);
        }

        @SuppressLint("SetTextI18n")
        public void setDetails(NewsItem n) {
            title.setText(n.getTitle());
            author.setText("by "+n.getAuthor());
            content_short.setText(n.getContent());
        }
    }
}
