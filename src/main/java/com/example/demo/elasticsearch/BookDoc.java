package com.example.demo.elasticsearch;

import java.util.Date;

public class BookDoc {
  private String author;
  private String title;
  private String content;
  private String price;
  private String view;
  private String tag;
  private Date date;

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getView() {
    return view;
  }

  public void setView(String view) {
    this.view = view;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "BookDoc{" +
        "author='" + author + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", price='" + price + '\'' +
        ", view='" + view + '\'' +
        ", tag='" + tag + '\'' +
        ", date=" + date +
        '}';
  }
}
