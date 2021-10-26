package ru.kpfu.itis.renett.models;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Comment {
    private Integer id;
    private String body;
    private Article article;
    private User author;
    private Date publishedAt;

    private Comment parentComment;
    private List<Comment> nestedComments;

    public Comment(Integer id, String body, Article article, User author, Date publishedAt, Comment parentComment, List<Comment> nestedComments) {
        this.id = id;
        this.body = body;
        this.article = article;
        this.author = author;
        this.publishedAt = publishedAt;
        this.parentComment = parentComment;
        this.nestedComments = nestedComments;
    }

    public Comment(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        int parentNum = -1;
        if (parentComment != null) {
            parentNum = parentComment.id;
        }

        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", article=" + article.getId() +
                ", author=" + author.getId() +
                ", publishedAt=" + publishedAt +
                ", parentComment=" + parentNum +
                ", nestedComments=" + nestedComments +
                '}';
    }
}
