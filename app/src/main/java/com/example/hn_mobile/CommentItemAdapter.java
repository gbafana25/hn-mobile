package com.example.hn_mobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.CommentItemHolder> {
    private Context context;
    private ArrayList<CommentItem> comments;

    public CommentItemAdapter(Context c, ArrayList<CommentItem> items) {
        this.context = c;
        this.comments = items;
    }

    @NonNull
    @Override
    public CommentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        return new CommentItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentItemHolder holder, int position) {
        CommentItem c = comments.get(position);
        holder.setDetails(c);
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    public int getItemViewType(int position) { return position; }


    public class CommentItemHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView content;
        public CommentItemHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.comment_author);
            content = itemView.findViewById(R.id.comment_content);


        }

        public void setDetails(CommentItem comment) {
            author.setText(comment.getAuthor());
            content.setText(comment.getContent());
        }
    }
}
