package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
public class Article {
    private Integer id;
    private String title;
    private String body;
    private User author;
    private Date publishedAt;
    private List<Comment> commentList;
    private List<Tag> tagList;
    private Long viewCount;
    private int commentAmount;
    private int likeAmount;

    public Article(Integer id) {
        this.id = id;
    }

    public Article(Integer id, String title, String body, User author, Date publishedAt, List<Comment> commentList, List<Tag> tagList, Long viewCount, int commentAmount, int likeAmount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.author = author;
        this.publishedAt = publishedAt;
        this.commentList = commentList;
        this.tagList = tagList;
        this.viewCount = viewCount;
        this.commentAmount = commentAmount;
        this.likeAmount = likeAmount;
    }

    public Article(Integer id, String title, String body, Date publishedAt, Long viewCount) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.publishedAt = publishedAt;
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", author=" + author +
                ", publishedAt=" + publishedAt +
                ", commentList=" + commentList +
                ", tagList=" + tagList +
                ", view_count=" + viewCount +
                ", commentAmount=" + commentAmount +
                ", likeAmount=" + likeAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id.equals(article.id) && title.equals(article.title) && body.equals(article.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body);
    }
}
