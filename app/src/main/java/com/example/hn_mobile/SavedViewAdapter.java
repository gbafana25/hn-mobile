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
        private Button see_more;
        public SavedHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            author = itemView.findViewById(R.id.author_text);
            content_short = itemView.findViewById(R.id.content_short);
            see_more = itemView.findViewById(R.id.see_btn);
        }

        @SuppressLint("SetTextI18n")
        public void setDetails(NewsItem n) {
            title.setText(n.getTitle());
            author.setText("by "+n.getAuthor());
            content_short.setText(n.getContent());

            see_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Objects.equals(n.getContentType(), "url")) {
                        Intent urlintent = new Intent(Intent.ACTION_VIEW, Uri.parse(n.getContent()));
                        context.startActivity(urlintent);

                    } else if(Objects.equals(n.getContentType(), "string")) {
                        Intent readerIntent = new Intent(context, FullItemView.class);
                        n.loadComments();
                        System.out.println(n.getCommentArraySize());
                        readerIntent.putExtra("title", n.getTitle());
                        readerIntent.putExtra("body", n.getFullContent());
                        readerIntent.putExtra("comments", n.getCommentArray());
                        context.startActivity(readerIntent);
                    }
                }
            });
        }
    }
}
