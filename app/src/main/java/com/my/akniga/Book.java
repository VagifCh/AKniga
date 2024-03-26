package com.my.akniga;
import java.io.Serializable;
public class Book implements Serializable{
    private String style;
    private String author;
    private String seria;
    private String bookCover;
    private String name;
    private String reader;
    private String duration;
    private String link;
    private String desc;


    public Book(String bookCover, String name, String author, String seria, String reader, String style, String duration, String link, String desc) {
        this.name = name;
        this.author = author;
        this.bookCover = bookCover;
        this.seria = seria;
        this.reader = reader;
        this.style = style;
        this.duration = duration;
        this.link = link;
        this.desc = desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setReader(String reader) {
        this.reader = reader;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDuration() {
        return duration;
    }

    public String getReader() {
        return reader;
    }

    public String getSeria() {
        return seria;
    }

    public String getStyle() {
        return style;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }
}
