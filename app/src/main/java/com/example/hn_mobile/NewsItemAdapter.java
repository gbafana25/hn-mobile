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
        private Button comment_btn;
        private Chip read_later;
        public NewsItemHolder(View view) {
            super(view);
            item_title = view.findViewById(R.id.newsitem_title);
            item_content = view.findViewById(R.id.newsitem_content);
            item_author = view.findViewById(R.id.newsitem_author);
            see_more = view.findViewById(R.id.see_more_btn);
            read_later = view.findViewById(R.id.read_later_chip);
            comment_btn = view.findViewById(R.id.comment_btn);

        }

        @SuppressLint("SetTextI18n")
        public void setDetails(NewsItem n) {
            item_title.setText(n.getTitle());
            item_content.setText(n.getContent());
            item_author.setText("by " +n.getAuthor());
            // read save file, if exists then set to saved
            //read_later.setChecked(n.getSaved());
            if(itemIsSaved(n, context)) {
                read_later.setEnabled(false);
                n.setSaved(true);

            } else {
                read_later.setEnabled(true);
            }
            /*
            if(n.getSaved()) {
                read_later.setChecked(true);
            }

             */

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
                        readerIntent.putParcelableArrayListExtra("comments", n.getCommentArray());
                        context.startActivity(readerIntent);
                    }
                }
            });

            read_later.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    // create storage file w/ json array: saved_items: []

                    //System.out.println(n.getTitle()+" has been toggled");
                    if(read_later.isChecked() && !storage_array.contains(n)) {
                        // check if already in save file
                        if(!itemIsSaved(n, context)) {
                            storage_array.add(n);
                        }


                    } else {
                        for(int i = 0; i < storage_array.size(); i++) {
                            if(Objects.equals(n.getTitle(), storage_array.get(i).getTitle())) {
                                n.setSaved(false);
                                storage_array.remove(i);
                                break;
                            }
                        }
                    }

                }
            });

            comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_size = n.getCommentIds().size();
                    if(id_size != 0) {
                        Intent commentIntent = new Intent(context, CommentView.class);
                        n.loadComments();
                        commentIntent.putParcelableArrayListExtra("comments", n.getCommentArray());
                        context.startActivity(commentIntent);
                    }
                }
            });


        }

        public boolean itemIsSaved(NewsItem it, Context context) {
            try {
                FileInputStream f = context.openFileInput("storage.json");
                InputStreamReader reader = new InputStreamReader(f);
                try(BufferedReader buf = new BufferedReader(reader)) {
                    boolean exists = false;
                    String l = buf.readLine();
                    while(l != null) {
                        JSONObject lineobj = new JSONObject(l);
                        if(lineobj.toString().equals(it.toJSON().toString())) {
                            exists = true;
                            break;
                        }
                        l = buf.readLine();
                    }
                    if(!exists) {
                        it.setSaved(true);
                        //storage_array.add(n);
                        return false;
                    } else {
                        //read_later.setEnabled(false);
                        //it.setSaved(false);
                        return true;
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
