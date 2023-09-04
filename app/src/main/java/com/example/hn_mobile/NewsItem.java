package com.example.hn_mobile;

public class NewsItem {
    private String title;
    private int score;
    private String content;
    private String author;
    // use type to determine if the text or url fields are available

    private String type;
    private int time;

    public NewsItem(String title, int score, String content, String author, String type, int time) {
        this.title = title;
        this.score = score;
        this.content = content;
        this.author = author;
        this.type = type;
        this.time = time;
    }

    public String getTitle() {
        return this.title;
    }
    public String getContent() { return this.content; }
    public String getAuthor() { return this.author; }
}
