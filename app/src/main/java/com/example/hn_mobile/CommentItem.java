package com.example.hn_mobile;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import androidx.annotation.NonNull;

public class CommentItem implements Parcelable {
    private String author;
    private String content;

    public CommentItem(String a, String c) {
        this.author = a;
        this.content = Html.fromHtml(c).toString();
    }

    public CommentItem(Parcel source) {
        author = source.readString();
        content = source.readString();
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }


    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CommentItem createFromParcel(Parcel in) {
            return new CommentItem(in);
        }
        public CommentItem[] newArray(int size) {
            return new CommentItem[size];
        }
    };
}
