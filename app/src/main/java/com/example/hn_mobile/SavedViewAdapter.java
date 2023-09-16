package com.example.hn_mobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        private ImageButton comments;
        private ImageButton delete;
        public SavedHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            author = itemView.findViewById(R.id.author_text);
            content_short = itemView.findViewById(R.id.content_short);
            see_more = itemView.findViewById(R.id.see_btn);
            comments = itemView.findViewById(R.id.comment_btn_saved);
            delete = itemView.findViewById(R.id.delete_btn);
        }

        @SuppressLint("SetTextI18n")
        public void setDetails(NewsItem n) {
            title.setText(n.getTitle());
            author.setText("by "+n.getAuthor());
            content_short.setText(n.getContent());

            see_more.setOnClickListener(view -> {
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
            });

            comments.setOnClickListener(view -> {
                int id_size = n.getCommentIds().size();
                if(id_size != 0) {
                    Intent commentIntent = new Intent(context, CommentView.class);
                    n.loadComments();
                    commentIntent.putExtra("comments", n.getCommentArray());
                    context.startActivity(commentIntent);
                }
            });

            delete.setOnClickListener(view -> {
                // delete from arraylist
                items.remove(n);

                // delete from savefile
                try {
                    FileInputStream fs = context.openFileInput("storage.json");
                    InputStreamReader raw_input = new InputStreamReader(fs);
                    ArrayList<String> cobjs = new ArrayList<>();
                    try(BufferedReader bf = new BufferedReader(raw_input)) {
                        String l = bf.readLine();
                        while(l != null) {
                            JSONObject json = new JSONObject(l);
                            // should be != ?
                            if(json.getString("title").equals(n.getTitle())) {
                                cobjs.add(l);
                            }

                            l = bf.readLine();
                        }
                        fs.close();
                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }

                    // write arraylist back to file
                    try(FileOutputStream out = context.openFileOutput("storage.json", Context.MODE_PRIVATE)) {
                        for(int i = 0; i < cobjs.size(); i++) {
                            out.write(cobjs.get(i).getBytes());
                        }
                    }
                    Intent ret = new Intent(context, MainActivity.class);
                    context.startActivity(ret);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
