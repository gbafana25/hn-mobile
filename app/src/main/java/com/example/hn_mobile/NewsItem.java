package com.example.hn_mobile;

public class NewsItem {
    private String title;
    private int score;
    private String content;
    private String content_full;
    private String author;
    // use type to determine if the text or url fields are available

    private String type;
    private int time;
    private String content_type; // url or string

    public NewsItem(String title, int score, String content, String author, String type, int time, String content_type, String content_full) {
        this.title = title;
        this.score = score;
        this.content = content;
        this.author = author;
        this.type = type;
        this.time = time;
        this.content_type = content_type;
        this.content_full = content_full;
    }

    public String getTitle() {
        return this.title;
    }
    public String getContent() { return this.content; }
    public String getAuthor() { return this.author; }
    public String getType() { return this.type; }
    public String getContentType() { return this.content_type; }
    public String getFullContent() { return this.content_full; }
}
